package com.waitless.restaurant.restaurant.application.exception;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.ErrorCode;

public class RestaurantBusinessException extends BusinessException {

    public RestaurantBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static RestaurantBusinessException from(ErrorCode errorCode) {
        return new RestaurantBusinessException(errorCode);
    }
}
