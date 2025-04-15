package com.waitless.reservation.application.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisReservationQueueService {

    private final StringRedisTemplate redisTemplate;

    public void registerToWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId, Long reservationNumber) {
        String zsetKey = "reservation:queue:" + restaurantId + ":" + reservationDate;
        redisTemplate.opsForZSet().add(zsetKey, String.valueOf(reservationId), reservationNumber);
    }

    public void removeFromWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId) {
        String zsetKey = "reservation:queue:" + restaurantId + ":" + reservationDate;
        redisTemplate.opsForZSet().remove(zsetKey, String.valueOf(reservationId));
    }
}