package com.waitless.benefit.point.infrastructure.adaptor.out.persistence;

import com.waitless.benefit.point.application.port.out.PointStatisticsCachePort;
import com.waitless.benefit.point.domain.vo.PointAmountCache;
import com.waitless.benefit.point.domain.vo.PointRankingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class PointStatisticsRedisRepository implements PointStatisticsCachePort {

    private final RedisTemplate<String, Object> pointRedisTemplate;
    private static final String POINT_AMOUNT_PREFIX = "point:amount:";
    private static final String POINT_RANKING_PREFIX = "point:ranking:me:";

    @Override
    public void saveAmount(Long userId, PointAmountCache cache, long ttlSeconds) {
        pointRedisTemplate.opsForValue().set(getAmountKey(userId), cache, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public PointAmountCache findAmount(Long userId) {
        return (PointAmountCache) pointRedisTemplate.opsForValue().get(getAmountKey(userId));
    }

    @Override
    public void saveMyRanking(Long userId, PointRankingCache cache, long ttlSeconds) {
        pointRedisTemplate.opsForValue().set(getRankingKey(userId), cache, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public PointRankingCache findMyRanking(Long userId) {
        return (PointRankingCache) pointRedisTemplate.opsForValue().get(getRankingKey(userId));
    }

    @Override
    public void deleteAmount(Long userId) {
        pointRedisTemplate.delete(getAmountKey(userId));
    }

    @Override
    public void deleteMyRanking(Long userId) {
        pointRedisTemplate.delete(getRankingKey(userId));
    }

    private String getAmountKey(Long userId) {
        return POINT_AMOUNT_PREFIX + userId;
    }

    private String getRankingKey(Long userId) {
        return POINT_RANKING_PREFIX + userId;
    }
}
