package com.waitless.reservation.application.event.dto;

import java.util.UUID;

public record VisitRequestEvent(UUID restaurantId) {
}
