package com.waitless.benefit.point.application.service.cache;

import com.waitless.benefit.point.application.dto.result.GetMyRankingResult;
import com.waitless.benefit.point.application.dto.result.GetPointAmountResult;
import com.waitless.benefit.point.application.port.out.PointRankingCachePort;
import com.waitless.benefit.point.application.port.out.PointStatisticsCachePort;
import com.waitless.benefit.point.domain.repository.PointRepositoryCustom;
import com.waitless.benefit.point.domain.vo.PointAmountCache;
import com.waitless.benefit.point.domain.vo.PointRankingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.waitless.common.constants.PointCacheConstants.TTL_SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointQueryCacheImpl implements PointQueryCache {

    private final PointRepositoryCustom pointRepositoryCustom;
    private final PointStatisticsCachePort pointStatisticsCachePort;
    private final PointRankingCachePort pointRankingCachePort;

    @Override
    public GetPointAmountResult getTotalPoint(Long userId) {
        // 1. Redis 캐시 조회
        PointAmountCache cached = pointStatisticsCachePort.findAmount(userId);
        if (cached != null) {
            log.debug("[CACHE HIT] getTotalPoint userId={}", userId);
            return GetPointAmountResult.of(userId, cached.getTotalPoint());
        }
        // 2. 캐시 MISS → DB 조회
        int total = pointRepositoryCustom.getTotalPointByUserId(userId);
        // 3. Redis 캐시 저장
        pointStatisticsCachePort.saveAmount(userId, PointAmountCache.builder()
                .totalPoint(total)
                .build(), TTL_SECONDS);
        return GetPointAmountResult.of(userId, total);
    }

    @Override
    public GetMyRankingResult getMyRanking(Long userId) {
        // 1. Redis 캐시 조회
        PointRankingCache cached = pointStatisticsCachePort.findMyRanking(userId);
        if (cached != null) {
            log.debug("[CACHE HIT] getMyRanking userId={}", userId);
            return GetMyRankingResult.of(cached.getUserId(), cached.getTotalPoint(), cached.getRank());
        }
        // 2. 캐시 MISS → Redis ZSET 조회
        Long rank = pointRankingCachePort.getMyRanking(userId);
        if (rank == null) {
            log.warn("[NO RANK FOUND] userId={}", userId);
            rank = 0L;
        }
        int total = pointRepositoryCustom.getTotalPointByUserId(userId);
        // 3. Redis 캐시 저장
        pointStatisticsCachePort.saveMyRanking(userId, PointRankingCache.builder()
                .userId(userId)
                .totalPoint(total)
                .rank(rank.intValue())
                .build(), TTL_SECONDS);
        return GetMyRankingResult.of(userId, total, rank.intValue());
    }
}
