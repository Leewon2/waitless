package com.waitless.reservation.application.dto;

import java.util.UUID;

public record ReservationServiceResponse(
        UUID reservationId,
        Long userId,
        UUID restaurantId
) {}
