package com.waitless.reservation.application.event.dto;

public record ReservationCancelRequestEvent(Long userId, String restaurantName) {
}
