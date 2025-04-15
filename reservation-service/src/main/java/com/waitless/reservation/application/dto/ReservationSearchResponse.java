package com.waitless.reservation.application.dto;

import java.util.UUID;

public record ReservationSearchResponse(UUID ReservationId, String RestaurantName, UUID RestaurantId, Long ReservationNumber, Integer peopleCount, Long userId) {
}
