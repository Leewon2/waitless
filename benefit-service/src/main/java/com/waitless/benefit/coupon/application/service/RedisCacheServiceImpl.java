package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

	private final CouponHistoryRepository couponHistoryRepository;

	@Cacheable(cacheNames = "CH", key = "#id")
	public CouponHistoryCacheDto getCouponHistory(UUID id) {
		log.info("Find CouponHistory By {}", id);
		CouponHistory couponHistory = couponHistoryRepository.findById(id)
			.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
		return new CouponHistoryCacheDto(
			couponHistory.getId(),
			couponHistory.getTitle(),
			couponHistory.getUserId(),
			couponHistory.getCouponId(),
			couponHistory.isValid(),
			couponHistory.getExpiredAt()
		);
	}

	@CachePut(cacheNames = "CH", key = "#couponHistory.id")
	public CouponHistoryCacheDto cacheCouponHistory(CouponHistory couponHistory) {
		log.info("Save CouponHistory In Redis : {}", couponHistory.getId());
		return new CouponHistoryCacheDto(
			couponHistory.getId(),
			couponHistory.getTitle(),
			couponHistory.getUserId(),
			couponHistory.getCouponId(),
			couponHistory.isValid(),
			couponHistory.getExpiredAt()
		);
	}

	// // Redis 캐싱
	// private void cacheCouponHistory(CouponHistory couponHistory) {
	// 	CouponHistoryCacheDto saved = new CouponHistoryCacheDto(
	// 		couponHistory.getId(), couponHistory.getTitle(), couponHistory.getUserId(), couponHistory.getCouponId(), couponHistory.isValid(), couponHistory.getExpiredAt()
	// 	);
	// 	redisTemplate.opsForValue().set("CH:" + couponHistory.getId(), saved, Duration.ofDays(1));
	// }
}
