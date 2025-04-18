package com.waitless.review.infrastructure.adaptor.out.client.dto;

import java.util.UUID;

public record ReservationServiceResponse(
        UUID reservationId,
        Long userId,
        UUID restaurantId
) {}
