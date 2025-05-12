package com.waitless.auth.application.exception;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.ErrorCode;

public class AuthBusinessException extends BusinessException {

	private AuthBusinessException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static AuthBusinessException from(ErrorCode errorCode) {
		return new AuthBusinessException(errorCode);
	}
}
