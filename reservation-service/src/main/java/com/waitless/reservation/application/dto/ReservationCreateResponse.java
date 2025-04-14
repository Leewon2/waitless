package com.waitless.reservation.application.dto;

import com.waitless.reservation.domain.entity.ReservationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReservationCreateResponse(
        UUID reservationId,
        UUID restaurantId,
        List<MenuDto> menus,
        String restaurantName,
        Integer peopleCount,
        LocalDate reservationDate,
        Long userId,
        Long reservationNumber,
        ReservationStatus status
) {
    public record MenuDto(
            UUID menuId,
            String menuName,
            Integer quantity,
            Integer price
    ) {}
}