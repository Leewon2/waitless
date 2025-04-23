package com.waitless.reservation.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReservationCreateRequest(
        @NotNull
        UUID restaurantId,

        @NotEmpty
        List<MenuDto> menus,

        @NotBlank
        String restaurantName,

        Integer peopleCount,
        LocalDate reservationDate) {
    public record MenuDto(
            @NotNull
            UUID menuId,
            @NotBlank
            String menuName,
            @NotNull
            Integer quantity,
            @NotNull
            Integer price
    ) {}
}