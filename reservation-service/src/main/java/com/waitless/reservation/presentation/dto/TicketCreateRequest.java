package com.waitless.reservation.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record TicketCreateRequest(

        @NotNull(message = "restaurantId는 필수입니다.")
        UUID restaurantId,

        @NotNull(message = "openTime은 필수입니다.")
        LocalTime openTime
) {
}