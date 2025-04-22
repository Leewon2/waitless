package com.waitless.review.infrastructure.adaptor.out.persistence;

import com.waitless.review.application.port.out.ReviewStatisticsCachePort;
import com.waitless.review.domain.vo.ReviewStatisticsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ReviewStatisticsRedisRepository implements ReviewStatisticsCachePort {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "review:stats:";

    public void save(String restaurantId, ReviewStatisticsCache data, long ttlSeconds) {
        String key = getKey(restaurantId);
        redisTemplate.opsForValue().set(key, data, ttlSeconds, TimeUnit.SECONDS);
    }

    public ReviewStatisticsCache find(String restaurantId) {
        String key = getKey(restaurantId);
        return (ReviewStatisticsCache) redisTemplate.opsForValue().get(key);
    }

    public void delete(String restaurantId) {
        redisTemplate.delete(getKey(restaurantId));
    }

    private String getKey(String restaurantId) {
        return KEY_PREFIX + restaurantId;
    }
}