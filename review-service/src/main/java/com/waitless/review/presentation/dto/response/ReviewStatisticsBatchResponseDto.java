package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
public record ReviewStatisticsBatchResponseDto(
        UUID restaurantId,
        long reviewCount,
        double averageRating
) {
    public static List<ReviewStatisticsBatchResponseDto> fromMap(Map<UUID, ReviewStatisticsResult> resultMap) {
        return resultMap.entrySet().stream()
                .map(entry -> {
                    UUID id = entry.getKey();
                    ReviewStatisticsResult result = entry.getValue();
                    return ReviewStatisticsBatchResponseDto.builder()
                            .restaurantId(id)
                            .reviewCount(result.reviewCount())
                            .averageRating(result.averageRating())
                            .build();
                })
                .toList();
    }
}