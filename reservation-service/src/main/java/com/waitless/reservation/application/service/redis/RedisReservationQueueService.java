package com.waitless.reservation.application.service.redis;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.CommonErrorCode;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final String QUEUE_PREFIX = "reservation:queue:";

    public void registerToWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId, Long reservationNumber) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        redisTemplate.opsForZSet().add(zsetKey, String.valueOf(reservationId), reservationNumber);
    }

    public void removeFromWaitingQueue(UUID reservationId, LocalDate reservationDate, UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        redisTemplate.opsForZSet().remove(zsetKey, String.valueOf(reservationId));
        message(restaurantId);
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

    private void message(UUID restaurantId) {
        String zsetKey = QUEUE_PREFIX + restaurantId;
        String alertKey = "reservation:alerted:" + restaurantId;

        // 현재 대기열 1번 사용자 가져오기
        var top1 = redisTemplate.opsForZSet().range(zsetKey, 0, 0);
        if (top1 == null || top1.isEmpty()) {
            log.info("[Queue] 식당 {}: 대기열 비어있음", restaurantId);
            return;
        }

        String currentTopReservationId = top1.iterator().next();

        redisTemplate.opsForValue().set(alertKey, currentTopReservationId);

        String lastReservationId = redisTemplate.opsForValue().get(alertKey);

        if (currentTopReservationId.equals(lastReservationId)) {
            log.info("[Queue] 식당 {}: 사용자 {}는 이미 알림 보냄", restaurantId, currentTopReservationId);
            return;
        }

        try {

            redisTemplate.opsForValue().set(alertKey, currentTopReservationId);
            log.info("[Queue] 식당 {}: 예약ID {}에게 입장 메시지 전송 완료", restaurantId, currentTopReservationId);
        } catch (Exception e) {
            log.error("[Queue] 식당 {}: 예약ID {}에게 메시지 전송 실패", restaurantId, currentTopReservationId, e);
        }
    }
}