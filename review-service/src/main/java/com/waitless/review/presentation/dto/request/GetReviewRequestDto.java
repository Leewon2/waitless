package com.waitless.review.presentation.dto.request;

import java.util.UUID;

public record GetReviewRequestDto(
        UUID reviewId
) {}
