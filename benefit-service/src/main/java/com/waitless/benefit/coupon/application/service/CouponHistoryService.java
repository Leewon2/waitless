package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;

public interface CouponHistoryService {
	CouponHistoryResponseDto issuedCoupon(UUID couponId, String userId);

	CouponHistoryResponseDto findCouponHistory(UUID id);
}
