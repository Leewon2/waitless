package com.waitless.benefit.coupon.presentation.dto;

import java.time.LocalDateTime;

public record CreateCouponRequestDto(String title, int amount, LocalDateTime issuanceDate, int validPeriod) {
}
