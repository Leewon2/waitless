package com.waitless.review.application.dto.client;

import java.util.UUID;

public record VisitedReservationRequestDto(
        UUID reservationId
) {}
