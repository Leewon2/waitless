package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CouponInfoDto(UUID id, String title, int discountRate, int amount, LocalDateTime issuanceDate, int validPeriod) {
}
