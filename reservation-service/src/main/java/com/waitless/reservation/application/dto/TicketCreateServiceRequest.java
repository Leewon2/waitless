package com.waitless.reservation.application.dto;

import java.time.LocalTime;
import java.util.UUID;

public record TicketCreateServiceRequest(UUID restaurantId, LocalTime openTime) {
}