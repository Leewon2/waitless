package com.waitless.reservation.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReservationCreateCommand(
        UUID restaurantId,
        List<MenuCommandDto> menus,
        String restaurantName,
        Integer peopleCount,
        LocalDate reservationDate) {
    public record MenuCommandDto(
            UUID menuId,
            String menuName,
            Integer quantity,
            Integer price
    ) {}
}