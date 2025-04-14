package com.waitless.reservation.application.service.query;

import com.waitless.reservation.presentation.dto.ReservationFindResponse;

import java.util.UUID;

public interface ReservationQueryService {
    ReservationFindResponse findOne(UUID reservationId);
}
