package com.waitless.benefit.coupon.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.waitless.benefit.coupon.domain.entity.Coupon;

public record CouponCacheDto(
	UUID id,
	String title,
	int amount,
	String issuanceDate,
	int validPeriod
) {
	public static CouponCacheDto from(Coupon coupon) {
		return new CouponCacheDto(
			coupon.getId(),
			coupon.getTitle(),
			coupon.getAmount(),
			coupon.getIssuanceDate().toString(), // String으로 변환
			coupon.getValidPeriod()
		);
	}

	public LocalDateTime issuanceDateAsLocalDateTime() {
		return LocalDateTime.parse(issuanceDate);
	}
}

