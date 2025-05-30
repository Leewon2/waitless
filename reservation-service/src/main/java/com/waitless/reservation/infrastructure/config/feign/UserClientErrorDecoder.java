package com.waitless.reservation.infrastructure.config.feign;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class UserClientErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return BusinessException.from(ReservationErrorCode.USER_NOT_FOUND);
        } else if (response.status() >= 500) {
            return BusinessException.from(ReservationErrorCode.USER_SERVER_ERROR);
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
