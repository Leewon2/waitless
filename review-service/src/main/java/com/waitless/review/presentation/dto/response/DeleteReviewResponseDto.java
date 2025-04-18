package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.DeleteReviewResult;

import java.util.UUID;

public record DeleteReviewResponseDto(
        UUID reviewId,
        boolean deleted
) {
    public static DeleteReviewResponseDto from(DeleteReviewResult result) {
        return new DeleteReviewResponseDto(
                result.reviewId(),
                result.deleted()
        );
    }
}
