package com.waitless.payment.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponHistoryInfoDto {

	private CouponHistoryData data;
	public static class CouponHistoryData {
		private UUID id;
		private String title;
		private Long userId;
		private UUID couponId;
		private LocalDateTime expiredAt;
	}

	private String errorMessage;
	private String errorCode;
}
