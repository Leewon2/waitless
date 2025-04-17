package com.waitless.benefit.coupon.application.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponHistoryServiceMapper;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.infrastructure.repository.CustomCouponHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponHistoryServiceImpl implements CouponHistoryService{

	private final CouponHistoryRepository couponHistoryRepository;
	private final CustomCouponHistoryRepository customCouponHistoryRepository;
	private final CouponHistoryServiceMapper couponHistoryServiceMapper;
	private final CouponService couponService;
	private final RedisTemplate<String, Object> redisTemplate;
	private final RedissonClient redissonClient;

	// 쿠폰 받기
	@Override
	@Transactional
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
			CouponResponseDto couponInfo = couponService.findCoupon(couponId);
			// 쿠폰 수량 확인 후
			if (couponInfo.amount() <= 0) {
				throw CouponBusinessException.from(CouponErrorCode.COUPON_AMOUNT_EXHAUSTED);
			}
			LocalDateTime today = LocalDateTime.now();
			// 쿠폰 발급 가능일자가 지나면 예외처리
			if (!today.isBefore(couponInfo.issuanceDate())) {
				throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
			}
			// 쿠폰 사용 가능 일자
			LocalDateTime expiredDate = today.plusDays(couponInfo.validPeriod());
			CouponHistory couponHistory = CouponHistory.builder()
				.title(couponInfo.title())
				.couponId(couponId)
				.userId(userId)
				.isValid(true)
				.expiredAt(expiredDate)
				.build();
			// 쿠폰 발급가능수량 -1 차감
			couponService.decreaseCouponAmount(couponId);
			CouponHistory saved = couponHistoryRepository.save(couponHistory);
			// Redis 캐싱
			redisTemplate.opsForValue().set("CH:"+ saved.getId(), couponHistory, Duration.ofHours(1));

			return couponHistoryServiceMapper.toCouponHistoryResponseDto(saved);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
		} finally {
			if (isLocked && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	// 쿠폰발급내역 단건 조회
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
	public void userIssuedCoupon(UUID id, Long userId) {
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
		couponHistory.used(couponHistory);
	}

	private CouponHistory findCouponHistoryById(UUID id) {
		String key = "CH:" + id;
		CouponHistory cached = (CouponHistory) redisTemplate.opsForValue().get(key);
		if (cached != null) {
			return cached;
		}
		CouponHistory couponHistory = couponHistoryRepository.findById(id)
			.orElseThrow(()-> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
		redisTemplate.opsForValue().set(key, couponHistory, Duration.ofHours(1));

		return couponHistory;
	}

}
