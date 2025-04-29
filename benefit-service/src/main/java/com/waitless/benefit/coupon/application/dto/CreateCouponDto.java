package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;

public record CreateCouponDto(String title, int discountRate, int amount, LocalDateTime issuanceDate, int validPeriod) {
}
