package com.waitless.user.application.exception;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.ErrorCode;

public class UserBusinessException extends BusinessException {

	private UserBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static UserBusinessException from(ErrorCode errorCode) {
		return new UserBusinessException(errorCode);
	}
}
