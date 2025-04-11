package com.waitless.reservation.application.service.command;

import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.presentation.dto.ReservationCreateResponse;

public interface ReservationCommandService {
    ReservationCreateResponse createReservation(ReservationCreateCommand reservationCreateCommand);
}
