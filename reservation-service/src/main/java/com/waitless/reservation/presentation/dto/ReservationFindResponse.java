package com.waitless.reservation.presentation.dto;

import com.waitless.reservation.domain.entity.ReservationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationFindResponse(String reservationId, String userId, ReservationStatus status,
                                      Integer delayCount, LocalDateTime createdAt, Integer peopleCount,
                                      String restaurantName, UUID restaurantId, List<ReservationMenuResponse> menus,
                                      Long reservationNumber, Integer totalPrice) implements Serializable {
}
