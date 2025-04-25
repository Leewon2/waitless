package com.waitless.reservation.presentation.dto;

import java.util.UUID;

public record ReservationServiceRequest(
        UUID reservationId
) {}
