package com.waitless.benefit.coupon.application.dto;

import java.util.UUID;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public record CouponHistoryCacheDto(
	UUID id, String title, Long userId, UUID couponId, boolean isValid, String expiredAt
) {
	public static CouponHistoryCacheDto from(CouponHistory couponHistory) {
		return new CouponHistoryCacheDto(
			couponHistory.getId(),
			couponHistory.getTitle(),
			couponHistory.getUserId(),
			couponHistory.getCouponId(),
			couponHistory.isValid(),
			couponHistory.getExpiredAt().toString()
		);
	}
}
