package com.waitless.reservation.application.service.redis;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisReservationService {

    private final StringRedisTemplate redisTemplate;
    private static final String RESERVATION_NUMBER_PREFIX = "reservation:number:";

    public Long createReservationNumber(UUID restaurantId) {
        return redisTemplate.opsForValue().increment(RESERVATION_NUMBER_PREFIX + restaurantId);
    }

    public void validateRestaurantExists(UUID restaurantId) {
        Boolean exists = redisTemplate.hasKey(RESERVATION_NUMBER_PREFIX + restaurantId);
        if (Boolean.FALSE.equals(exists)) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_RESTAURANT_NOT_FOUND);
        }
    }
}