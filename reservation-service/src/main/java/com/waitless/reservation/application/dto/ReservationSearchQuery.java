package com.waitless.reservation.application.dto;

import com.waitless.reservation.domain.entity.ReservationStatus;

public record ReservationSearchQuery(
        ReservationStatus status,
        Long userId,
        int page,
        int size,
        String sortBy,
        String direction) {}