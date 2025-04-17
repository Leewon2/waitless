package com.waitless.review.presentation.dto.request;

import java.util.UUID;

public record GetReviewListRequestDto(
        Long userId,
        UUID restaurantId
) {}
