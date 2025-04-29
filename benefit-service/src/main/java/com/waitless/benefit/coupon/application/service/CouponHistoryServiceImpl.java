package com.waitless.benefit.coupon.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponHistoryServiceMapper;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CustomCouponHistoryRepository;

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
	private final RedissonClient redissonClient;
	private final RedisCacheService redisCacheService;

	// 쿠폰 받기
	@Override
	public CouponHistoryResponseDto issuedCoupon(UUID couponId, Long userId) {
		String lockKey = "LOCK:CH:" + couponId;
		RLock lock = redissonClient.getLock(lockKey);
		boolean isLocked = false;
		try {
			isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
			if (!isLocked) {
				throw CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_TRY_AGAIN);
			}
			return issueCouponWithLock(couponId, userId);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("쿠폰 발급 중 예외 발생 : couponId={}, userId={}", couponId, userId, e);
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
		} finally {
			if (isLocked && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	private CouponHistoryResponseDto issueCouponWithLock(UUID couponId, Long userId) {
		LocalDateTime now = LocalDateTime.now();
		Coupon coupon = couponService.decreaseCouponAmount(couponId);
		if (!now.isBefore(coupon.getIssuanceDate())) {
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
		}
		CouponHistory couponHistory = CouponHistory.builder()
			.title(coupon.getTitle())
			.couponId(coupon.getId())
			.userId(userId)
			.isValid(true)
			.expiredAt(now.plusDays(coupon.getValidPeriod()))
			.build();
		CouponHistory saved = couponHistoryRepository.save(couponHistory);
		redisCacheService.cacheCouponHistory(saved);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(saved);
	}

	// 쿠폰발급내역 단건 조회
	@Override
	public CouponHistoryResponseDto findCouponHistory(UUID id) {
		CouponHistoryCacheDto cached = redisCacheService.getCouponHistory(id);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(cached);
	}

	// 쿠폰발급내역 목록 조회 + 검색
	@Override
	public Page<CouponHistoryResponseDto> findAndSearchCouponHistories(ReadCouponHistoriesDto readCouponHistoriesDto, Pageable pageable) {
		Page<CouponHistory> couponHistoryList = customCouponHistoryRepository.findAndSearchCouponHistories(
			readCouponHistoriesDto.title(),
			readCouponHistoriesDto.sortDirection(),
			readCouponHistoriesDto.sortBy(),
			readCouponHistoriesDto.userId(),
			readCouponHistoriesDto.role(),
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
		couponHistoryRepository.save(couponHistory);
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
		redisCacheService.cacheCouponHistory(couponHistory);
	}

	// 쿠폰발급내역 단건 조회(DB)
	private CouponHistory findCouponHistoryById(UUID id) {
		CouponHistory couponHistory = couponHistoryRepository.findById(id)
			.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
		return couponHistory;
	}

}
