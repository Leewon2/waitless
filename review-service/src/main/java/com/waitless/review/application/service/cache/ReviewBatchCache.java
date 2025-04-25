package com.waitless.review.application.service.cache;

import com.waitless.review.application.dto.result.ReviewStatisticsResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReviewBatchCache {
    Map<UUID, ReviewStatisticsResult> getBatch(List<UUID> restaurantIds);
}