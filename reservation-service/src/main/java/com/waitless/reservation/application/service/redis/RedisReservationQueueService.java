package com.waitless.reservation.application.service.redis;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.CommonErrorCode;
import com.waitless.reservation.application.event.dto.VisitRequestEvent;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisReservationQueueService {

    private final StringRedisTemplate redisTemplate;
    private final RedisLuaScriptService redisLuaScriptService;
    private final ApplicationEventPublisher eventPublisher;

    private static final String QUEUE_PREFIX = "reservation:queue:";

    public void registerToWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId, Long reservationNumber) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        redisTemplate.opsForZSet().add(zsetKey, String.valueOf(reservationId), reservationNumber);
    }

    public void removeFromWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        redisTemplate.opsForZSet().remove(zsetKey, String.valueOf(reservationId));
        eventPublisher.publishEvent(new VisitRequestEvent(restaurantId));
    }

    public Long findCurrentNumberFromWaitingQueue(UUID reservationId, UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        Long rank = redisTemplate.opsForZSet().rank(zsetKey, reservationId.toString());

        if(rank == null){
            throw BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND);
        }

        return rank + 1;
    }

    public void delayReservation(UUID reservationId, Long newPosition, UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        String luaScript = redisLuaScriptService.loadScript("classpath:lua/reservation_delay.lua");

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Boolean.class);

        try {
            redisTemplate.execute(
                    redisScript,
                    Collections.singletonList(zsetKey),
                    reservationId.toString(),
                    String.valueOf(newPosition)
            );
        } catch (Exception e) {
            log.error("순번 미루기 LuaScript 오류", e);
            throw BusinessException.from(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}