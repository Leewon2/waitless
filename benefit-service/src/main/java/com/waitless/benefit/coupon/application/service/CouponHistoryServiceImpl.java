package com.waitless.benefit.coupon.application.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponHistoryServiceMapper;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;
import com.waitless.benefit.coupon.infrastructure.repository.CustomCouponHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponHistoryServiceImpl implements CouponHistoryService{

	private final CouponHistoryRepository couponHistoryRepository;
	private final CustomCouponHistoryRepository customCouponHistoryRepository;
	private final CouponHistoryServiceMapper couponHistoryServiceMapper;
	private final CouponService couponService;
	private final CouponRepository couponRepository;
	private final RedisTemplate<String, Object> redisTemplate;
	private final RedissonClient redissonClient;
	private final ObjectMapper objectMapper;

	// 쿠폰 받기
	@Override
	// @Transactional
	public CouponHistoryResponseDto issuedCoupon(UUID couponId, Long userId) {
		String lockKey = "LOCK:CH:" + couponId;
		RLock lock = redissonClient.getLock(lockKey);
		boolean isLocked = false;
		try {
			isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
			if (!isLocked) {
				throw CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_TRY_AGAIN);
			}
			// 쿠폰 발급 시작
			// 쿠폰 조회와 쿠폰 수량 차감 동시에 진행
			Coupon coupon = couponService.decreaseCouponAmount(couponId);
			System.out.println("coupon amount = " + coupon.getAmount());
			couponRepository.save(coupon);
			LocalDateTime today = LocalDateTime.now();
			// 쿠폰 발급 가능일자가 지나면 예외처리
			if (!today.isBefore(coupon.getIssuanceDate())) {
				throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
			}
			// 쿠폰 사용 가능 일자
			LocalDateTime expiredDate = today.plusDays(coupon.getValidPeriod());
			CouponHistory couponHistory = CouponHistory.builder()
				.title(coupon.getTitle())
				.couponId(couponId)
				.userId(userId)
				.isValid(true)
				.expiredAt(expiredDate)
				.build();
			CouponHistory saved = couponHistoryRepository.save(couponHistory);
			// Redis 캐싱
			cachingCouponHistory(saved);

			return couponHistoryServiceMapper.toCouponHistoryResponseDto(saved);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
		} finally {
			if (isLocked && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	// 쿠폰발급내역 단건 조회 (API)
	@Override
	public CouponHistoryResponseDto findCouponHistory(UUID id) {
		CouponHistory couponHistory = findCouponHistoryById(id);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(couponHistory);
	}

	// 쿠폰발급내역 목록 조회 + 검색
	@Override
	public Page<CouponHistoryResponseDto> findAndSearchCouponHistories(ReadCouponHistoriesDto readCouponHistoriesDto, Pageable pageable) {
		Page<CouponHistory> couponHistoryList = customCouponHistoryRepository.findAndSearchCouponHistories(
			readCouponHistoriesDto.title(),
			readCouponHistoriesDto.sortDirection(),
			readCouponHistoriesDto.sortBy(),
			readCouponHistoriesDto.userId(),
			pageable
		);
		List<CouponHistoryResponseDto> dtoList = couponHistoryList
			.stream()
			.map(couponHistoryServiceMapper::toCouponHistoryResponseDto)
			.toList();
		return new PageImpl<>(dtoList, pageable, couponHistoryList.getTotalElements());
	}

	// 쿠폰발급내역 삭제
	@Override
	@Transactional
	public void removeCouponHistory(UUID id, Long userId) {
		CouponHistory couponHistory = findCouponHistoryById(id);
		if (!couponHistory.getUserId().equals(userId)) {
			throw CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_UNAUTHORIZED);
		}
		couponHistory.delete();
	}

	// 발급된 쿠폰 사용
	@Override
	@Transactional
	public void useIssuedCoupon(UUID id, Long userId) {
		CouponHistory couponHistory = findCouponHistoryById(id);
		if (!couponHistory.getUserId().equals(userId)) {
			throw CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_UNAUTHORIZED);
		}
		if (couponHistory.getExpiredAt().isBefore(LocalDateTime.now())) {
			throw CouponBusinessException.from(CouponErrorCode.ISSUED_COUPON_EXPIRED);
		}
		if (!couponHistory.isValid()) {
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ALREADY_USED);
		}
		couponHistory.used();
		couponHistoryRepository.save(couponHistory);
		// 캐시 업데이트
		cachingCouponHistory(couponHistory);
	}

	// 쿠폰발급내역 단건 조회
	private CouponHistory findCouponHistoryById(UUID id) {
		Object cachedJson = redisTemplate.opsForValue().get("CH:" + id);
		CouponHistoryCacheDto cached = objectMapper.convertValue(cachedJson, CouponHistoryCacheDto.class);
		return Optional.ofNullable(couponHistoryServiceMapper.toCouponHistory(cached)).orElseGet(() -> {
			CouponHistory couponHistory = couponHistoryRepository.findById(id)
				.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
			cachingCouponHistory(couponHistory);
			return couponHistory;
		});
	}

	// Redis 캐싱
	private void cachingCouponHistory(CouponHistory couponHistory) {
		CouponHistoryCacheDto saved = new CouponHistoryCacheDto(
			couponHistory.getId(), couponHistory.getTitle(), couponHistory.getUserId(), couponHistory.getCouponId(), couponHistory.isValid(), couponHistory.getExpiredAt()
		);
		redisTemplate.opsForValue().set("CH:" + couponHistory.getId(), saved, Duration.ofDays(1));
	}

}
