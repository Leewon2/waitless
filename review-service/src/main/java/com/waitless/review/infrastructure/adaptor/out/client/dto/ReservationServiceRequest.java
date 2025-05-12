package com.waitless.review.infrastructure.adaptor.out.client.dto;

import java.util.UUID;

public record ReservationServiceRequest(
        UUID reservationId
) {}
