package com.waitless.benefit.coupon.application.exception;

import org.springframework.http.HttpStatus;

import com.waitless.common.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public enum CouponErrorCode implements ErrorCode {

	COUPON_NOT_FOUND("COUPON_001", "해당 쿠폰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	COUPON_ISSUED_IMPOSSIBLE("COUPON_002", "해당 쿠폰을 발급할 수 있는 날짜가 지났습니다.", HttpStatus.BAD_REQUEST);

	private final String code;
	private final String message;
	private final int status;

	CouponErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
