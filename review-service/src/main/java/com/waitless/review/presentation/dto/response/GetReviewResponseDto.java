package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.GetReviewResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetReviewResponseDto(
        UUID reviewId,
        Long userId,
        UUID restaurantId,
        String content,
        int rating,
        String reviewType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetReviewResponseDto from(GetReviewResult result) {
        return GetReviewResponseDto.builder()
                .reviewId(result.reviewId())
                .userId(result.userId())
                .restaurantId(result.restaurantId())
                .content(result.content())
                .rating(result.rating().getRatingValue())
                .reviewType(result.type().getReviewType().name())
                .createdAt(result.createdAt())
                .updatedAt(result.updatedAt())
                .build();
    }
}
