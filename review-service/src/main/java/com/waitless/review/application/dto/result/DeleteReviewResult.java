package com.waitless.review.application.dto.result;

import com.waitless.review.domain.entity.Review;

import java.util.UUID;

public record DeleteReviewResult(
        UUID reviewId,
        boolean deleted
) {
    public static DeleteReviewResult from(Review review) {
        return new DeleteReviewResult(
                review.getId(),
                review.getType().isTypeDeleted()
        );
    }
}
