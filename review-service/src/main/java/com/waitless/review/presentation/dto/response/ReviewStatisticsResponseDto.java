package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import lombok.Builder;

@Builder
public record ReviewStatisticsResponseDto(
        long reviewCount,
        double averageRating
) {
    public static ReviewStatisticsResponseDto from(ReviewStatisticsResult result) {
        return ReviewStatisticsResponseDto.builder()
                .reviewCount(result.reviewCount())
                .averageRating(result.averageRating())
                .build();
    }
}