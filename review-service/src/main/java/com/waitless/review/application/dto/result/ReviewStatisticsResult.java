package com.waitless.review.application.dto.result;

import lombok.Builder;

@Builder
public record ReviewStatisticsResult(
        long reviewCount,
        double averageRating
) {
    public static ReviewStatisticsResult from(long count, double avg) {
        return ReviewStatisticsResult.builder()
                .reviewCount(count)
                .averageRating(avg)
                .build();
    }
}