package com.waitless.benefit.point.application.service.cache;

import com.waitless.benefit.point.application.dto.result.GetMyRankingResult;
import com.waitless.benefit.point.application.dto.result.GetPointAmountResult;

public interface PointQueryCache {
    GetPointAmountResult getTotalPoint(Long userId);
    GetMyRankingResult getMyRanking(Long userId);
}
