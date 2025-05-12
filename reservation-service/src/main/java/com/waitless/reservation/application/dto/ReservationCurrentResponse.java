package com.waitless.reservation.application.dto;

import com.waitless.reservation.domain.entity.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationCurrentResponse(Long currentNumber, UUID reservationId, Long ReservationNumber, String restaurantName, Integer peopleCount, LocalDateTime createdAt, UUID restaurantId) {
    public static ReservationCurrentResponse of(Reservation reservation, Long rank) {
        return new ReservationCurrentResponse(
                rank,
                reservation.getId(),
                reservation.getReservationNumber(),
                reservation.getRestaurantName(),
                reservation.getPeopleCount(),
                reservation.getCreatedAt(),
                reservation.getRestaurantId()
        );
    }
}
