package com.waitless.benefit.coupon.application.exception;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.ErrorCode;

public class CouponBusinessException extends BusinessException {

	private CouponBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static CouponBusinessException from(ErrorCode errorCode) {
		return new CouponBusinessException(errorCode);
	}
}
