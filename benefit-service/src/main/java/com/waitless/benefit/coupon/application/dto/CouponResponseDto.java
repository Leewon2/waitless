package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CouponResponseDto(UUID id, String title, int amount, LocalDateTime issuanceDate, int validPeriod) {
}