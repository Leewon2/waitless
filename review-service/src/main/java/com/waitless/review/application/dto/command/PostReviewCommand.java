package com.waitless.review.application.dto.command;

import java.util.UUID;

public record PostReviewCommand(
        UUID reservationId,
        Long userId,
        UUID restaurantId,
        String content,
        Integer rating
) {}
