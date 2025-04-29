package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CouponHistoryResponseDto(
	UUID id, String title, int discountRate, Long userId, UUID couponId, LocalDateTime expiredAt) {
}
