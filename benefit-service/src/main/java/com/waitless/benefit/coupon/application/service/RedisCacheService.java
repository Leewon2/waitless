package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import com.waitless.benefit.coupon.application.dto.CouponCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public interface RedisCacheService {
	CouponCacheDto getCoupon(UUID id);

	CouponCacheDto cacheCoupon(Coupon coupon);

	CouponHistoryCacheDto getCouponHistory(UUID id);

	CouponHistoryCacheDto cacheCouponHistory(CouponHistory couponHistory);
}
