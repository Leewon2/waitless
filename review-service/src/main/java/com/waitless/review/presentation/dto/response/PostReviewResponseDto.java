package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.domain.vo.Rating;
import com.waitless.review.domain.vo.ReviewType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostReviewResponseDto(
    UUID reviewId,
    UUID reservationId,
    Long userId,
    UUID restaurantId,
    String content,
    int rating,
    String reviewType,
    LocalDateTime createdAt
    ) {
    public static PostReviewResponseDto from(PostReviewResult result) {
        return new PostReviewResponseDto(
                result.reviewId(),
                result.reservationID(),
                result.userId(),
                result.restaurantId(),
                result.content(),
                result.rating().getRatingValue(),
                result.reviewType().getReviewType().name(),
                result.createdAt()
        );
    }
}
