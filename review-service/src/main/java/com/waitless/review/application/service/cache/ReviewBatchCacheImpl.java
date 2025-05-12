package com.waitless.review.application.service.cache;

import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import com.waitless.review.application.port.out.ReviewStatisticsCachePort;
import com.waitless.review.domain.repository.ReviewRepositoryCustom;
import com.waitless.review.domain.repository.ReviewStatisticsProjection;
import com.waitless.review.domain.vo.ReviewStatisticsCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.waitless.common.constants.ReviewCacheConstants.LOG_PREFIX;
import static com.waitless.common.constants.ReviewCacheConstants.TTL_SECONDS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewBatchCacheImpl implements ReviewBatchCache {

    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final ReviewStatisticsCachePort reviewStatisticsCachePort;

    @Override
    public Map<UUID, ReviewStatisticsResult> getBatch(List<UUID> restaurantIds) {
        if (restaurantIds == null || restaurantIds.isEmpty())
            return Map.of();

        Map<UUID, ReviewStatisticsResult> resultMap = new HashMap<>();
        Map<String, ReviewStatisticsCache> cachedMap = getCachedStatistics(restaurantIds);
        log.debug("{} Batch HIT: {}", LOG_PREFIX, cachedMap.keySet());

        addCacheHitsToResult(restaurantIds, cachedMap, resultMap);

        List<UUID> missedIds = findMissedIds(restaurantIds, resultMap);
        log.debug("{} Batch MISS: {}", LOG_PREFIX, missedIds);

        Map<UUID, ReviewStatisticsProjection> dbResults = getStatisticsFromDatabase(missedIds);
        updateResultsAndCache(dbResults, resultMap);

        return resultMap;
    }

    // Redis 캐시 조회
    private Map<String, ReviewStatisticsCache> getCachedStatistics(List<UUID> restaurantIds) {
        List<String> ids = restaurantIds.stream().map(UUID::toString).toList();
        return reviewStatisticsCachePort.findBatch(ids);
    }

    // 캐시 Hit 처리
    private void addCacheHitsToResult(
            List<UUID> restaurantIds,
            Map<String, ReviewStatisticsCache> cachedMap,
            Map<UUID, ReviewStatisticsResult> resultMap
    ) {
        for (UUID id : restaurantIds) {
            ReviewStatisticsCache cached = cachedMap.get(id.toString());
            if (cached != null) {
                resultMap.put(id, ReviewStatisticsResult.from(cached.getReviewCount(), cached.getAverageRating()));
            }
        }
    }

    // 캐시 MissId만 추출
    private List<UUID> findMissedIds(List<UUID> allIds, Map<UUID, ReviewStatisticsResult> resultMap) {
        return allIds.stream()
                .filter(id -> !resultMap.containsKey(id))
                .toList();
    }

    // DB 조회
    private Map<UUID, ReviewStatisticsProjection> getStatisticsFromDatabase(List<UUID> missedIds) {
        return reviewRepositoryCustom.findStatisticsByRestaurantIds(missedIds);
    }

    // 캐시에 저장할 데이터 구성 + Redis 캐시에 저장
    private void updateResultsAndCache(
            Map<UUID, ReviewStatisticsProjection> dbResults,
            Map<UUID, ReviewStatisticsResult> resultMap
    ) {
        Map<String, ReviewStatisticsCache> cacheMap = convertToResultAndCacheMap(dbResults, resultMap);
        saveCacheBatch(cacheMap);
    }

    // 캐시에 저장할 데이터 구성
    private Map<String, ReviewStatisticsCache> convertToResultAndCacheMap(
            Map<UUID, ReviewStatisticsProjection> dbResults,
            Map<UUID, ReviewStatisticsResult> resultMap
    ) {
        Map<String, ReviewStatisticsCache> cacheToSave = new HashMap<>();

        dbResults.forEach((id, projection) -> {
            long count = projection.getReviewCount();
            double avg = projection.getAverageRating();

            ReviewStatisticsResult result = ReviewStatisticsResult.from(count, avg);
            resultMap.put(id, result);

            ReviewStatisticsCache cache = ReviewStatisticsCache.builder()
                    .reviewCount(count)
                    .averageRating(avg)
                    .build();
            cacheToSave.put(id.toString(), cache);
        });
        return cacheToSave;
    }

    // Redis 캐시에 저장
    private void saveCacheBatch(Map<String, ReviewStatisticsCache> cacheToSave) {
        reviewStatisticsCachePort.saveBatch(cacheToSave, TTL_SECONDS);
    }

}