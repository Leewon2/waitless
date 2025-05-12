package com.waitless.reservation.application.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 테스트를 위해 웹 종료하면 redis 데이터 삭제
 */
@Component
@RequiredArgsConstructor
public class RedisShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        Set<String> reservationKeys = redisTemplate.keys("reservation:*");
        redisTemplate.delete(reservationKeys);
        Set<String> stockKeys = redisTemplate.keys("stock:*");
        redisTemplate.delete(stockKeys);
        System.out.println("Redis 정리 완료 (ContextClosedEvent)");
    }
}