package com.waitless.benefit.coupon.application.exception;

import org.springframework.http.HttpStatus;

import com.waitless.common.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public enum CouponErrorCode implements ErrorCode {

	COUPON_NOT_FOUND("USER_1", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

	private final String code;
	private final String message;
	private final int status;

	CouponErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
