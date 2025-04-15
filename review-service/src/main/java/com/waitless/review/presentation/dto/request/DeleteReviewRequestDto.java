package com.waitless.review.presentation.dto.request;

import java.util.UUID;

public record DeleteReviewRequestDto(
        UUID reviewId,
        Long userId
) {}
