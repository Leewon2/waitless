package com.waitless.reservation.application.dto;

import java.time.LocalTime;
import java.util.UUID;

public record TicketCreateServiceResponse(UUID restaurantId, LocalTime openTime) {
}