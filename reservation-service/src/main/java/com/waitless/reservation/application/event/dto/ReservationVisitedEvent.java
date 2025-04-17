package com.waitless.reservation.application.event.dto;

import java.util.UUID;

public record ReservationVisitedEvent(UUID reservationId, String restaurantName, Long userId) {}