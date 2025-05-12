package com.waitless.reservation.application.service.command;

import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.application.dto.ReservationCreateResponse;

import java.util.UUID;

public interface ReservationCommandService {
    ReservationCreateResponse createReservation(ReservationCreateCommand reservationCreateCommand);

    void cancelReservation(UUID reservationId);

    void visitReservation(UUID reservationId);

    void delayReservation(UUID reservationId, Long count);
}
