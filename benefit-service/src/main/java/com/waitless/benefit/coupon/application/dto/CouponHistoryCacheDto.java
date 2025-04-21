package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CouponHistoryCacheDto(
	UUID id, String title, Long userId, UUID couponId, boolean isValid, LocalDateTime expiredAt
) {
}
