package com.waitless.review.infrastructure.adaptor.out.persistence;

import com.waitless.review.application.port.out.ReviewStatisticsCachePort;
import com.waitless.review.domain.vo.ReviewStatisticsCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ReviewStatisticsRedisRepository implements ReviewStatisticsCachePort {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "review:stats:";

    @Override
    public void saveBatch(Map<String, ReviewStatisticsCache> data, long ttlSeconds) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            data.forEach((restaurantId, stats) -> {
                String key = getKey(restaurantId);
                redisTemplate.opsForValue().set(key, stats, ttlSeconds, TimeUnit.SECONDS);
            });
            return null;
        });
    }

    @Override
    public Map<String, ReviewStatisticsCache> findBatch(List<String> restaurantIds) {
        List<String> keys = restaurantIds.stream().map(this::getKey).toList();
        List<Object> values = redisTemplate.opsForValue().multiGet(keys);

        Map<String, ReviewStatisticsCache> result = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            if (values.get(i) != null) {
                result.put(restaurantIds.get(i), (ReviewStatisticsCache) values.get(i));
            }
        }
        return result;
    }

    public void delete(String restaurantId) {
        redisTemplate.delete(getKey(restaurantId));
    }

    private String getKey(String restaurantId) {
        return KEY_PREFIX + restaurantId;
    }
}