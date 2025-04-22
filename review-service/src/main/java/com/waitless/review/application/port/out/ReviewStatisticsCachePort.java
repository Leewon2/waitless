package com.waitless.review.application.port.out;

import com.waitless.review.domain.vo.ReviewStatisticsCache;

public interface ReviewStatisticsCachePort {
    void save(String restaurantId, ReviewStatisticsCache data, long ttlSeconds);
    ReviewStatisticsCache find(String restaurantId);
    void delete(String restaurantId);
}