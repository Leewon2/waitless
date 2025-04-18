package com.waitless.review.application.dto.client;

import java.util.UUID;

public record VisitedReservationResponseDto(
        UUID reservationId,
        Long userId,
        UUID restaurantId
) {}
