package com.waitless.common.exception;

import com.waitless.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Object data;

    public static BusinessException from(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }

    public static BusinessException from(ErrorCode errorCode, Object data) {
        return new BusinessException(errorCode, data);
    }

    protected BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = null;
    }

    protected BusinessException(ErrorCode errorCode, Object data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }
}
