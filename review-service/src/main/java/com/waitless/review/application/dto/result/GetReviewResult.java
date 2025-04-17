package com.waitless.review.application.dto.result;

import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.vo.Rating;
import com.waitless.review.domain.vo.ReviewType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetReviewResult(
        UUID reviewId,
        Long userId,
        UUID restaurantId,
        String content,
        Rating rating,
        ReviewType type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetReviewResult from(Review review) {
        return GetReviewResult.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .restaurantId(review.getRestaurantId())
                .content(review.getContent())
                .rating(review.getRating())
                .type(review.getType())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
