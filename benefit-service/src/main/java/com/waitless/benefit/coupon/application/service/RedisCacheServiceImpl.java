package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.waitless.benefit.coupon.application.dto.CouponCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

	private final CouponRepository couponRepository;
	private final CouponHistoryRepository couponHistoryRepository;

	@Cacheable(cacheNames = "C", key = "#id")
	public CouponCacheDto getCoupon(UUID id) {
		log.info("Coupon Not Found In Redis {}", id);
		Coupon coupon = couponRepository.findById(id)
			.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPON_NOT_FOUND));
		return CouponCacheDto.from(coupon);
	}

	@CachePut(cacheNames = "C", key = "#coupon.id")
	public CouponCacheDto cacheCoupon(Coupon coupon) {
		log.info("Save Coupon In Redis : {}", coupon.getId());
		return CouponCacheDto.from(coupon);
	}

	@Cacheable(cacheNames = "CH", key = "#id")
	public CouponHistoryCacheDto getCouponHistory(UUID id) {
		log.info("CouponHistory Not Found In Redis {}", id);
		CouponHistory couponHistory = couponHistoryRepository.findById(id)
			.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
		return CouponHistoryCacheDto.from(couponHistory);
	}

	@CachePut(cacheNames = "CH", key = "#couponHistory.id")
	public CouponHistoryCacheDto cacheCouponHistory(CouponHistory couponHistory) {
		log.info("Save CouponHistory In Redis : {}", couponHistory.getId());
		return CouponHistoryCacheDto.from(couponHistory);
	}

	// // Redis 캐싱
	// private void cacheCouponHistory(CouponHistory couponHistory) {
	// 	CouponHistoryCacheDto saved = new CouponHistoryCacheDto(
	// 		couponHistory.getId(), couponHistory.getTitle(), couponHistory.getUserId(), couponHistory.getCouponId(), couponHistory.isValid(), couponHistory.getExpiredAt()
	// 	);
	// 	redisTemplate.opsForValue().set("CH:" + couponHistory.getId(), saved, Duration.ofDays(1));
	// }
}
