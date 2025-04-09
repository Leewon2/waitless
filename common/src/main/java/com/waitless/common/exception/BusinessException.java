package com.waitless.common.exception;

import com.waitless.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public static BusinessException from(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
