package com.waitless.review.application.dto.result;

import java.util.UUID;

public record DeleteReviewResult(
        UUID reviewId,
        boolean deleted
) {
    public static DeleteReviewResult from(UUID reviewId) {
        return new DeleteReviewResult(reviewId, true);
    }
}
