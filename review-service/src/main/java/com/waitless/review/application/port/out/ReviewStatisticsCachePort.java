package com.waitless.review.application.port.out;

import com.waitless.review.domain.vo.ReviewStatisticsCache;

import java.util.List;
import java.util.Map;

public interface ReviewStatisticsCachePort {
    void saveBatch(Map<String, ReviewStatisticsCache> data, long ttlSeconds);
    Map<String, ReviewStatisticsCache> findBatch(List<String> restaurantIds);
    void delete(String restaurantId);
}