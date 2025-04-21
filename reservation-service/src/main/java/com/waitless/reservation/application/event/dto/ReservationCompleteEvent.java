package com.waitless.reservation.application.event.dto;

public record ReservationCompleteEvent(Long userId, Long sequence) {
}
