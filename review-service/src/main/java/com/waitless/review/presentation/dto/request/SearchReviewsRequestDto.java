package com.waitless.review.presentation.dto.request;

import java.util.UUID;

public record SearchReviewsRequestDto(
        Long userId,
        UUID restaurantId,
        Integer rating
) {}
