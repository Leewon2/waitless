package com.waitless.reservation.application.event;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 테스트를 위해 웹 종료하면 redis 데이터 삭제
 */
@Component
@RequiredArgsConstructor
public class RedisShutdownListener {

    private final StringRedisTemplate redisTemplate;

    @PreDestroy
    public void cleanUp() {
        redisTemplate.delete(redisTemplate.keys("reservation:*"));
        redisTemplate.delete(redisTemplate.keys("stock:*"));
        System.out.println("Redis 정리 완료 (@PreDestroy)");
    }
}