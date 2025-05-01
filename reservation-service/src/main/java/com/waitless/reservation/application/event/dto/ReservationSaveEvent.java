package com.waitless.reservation.application.event.dto;

import com.waitless.common.dto.StockDto;
import com.waitless.reservation.domain.entity.Reservation;

import java.util.List;

public record ReservationSaveEvent(Reservation reservation, List<StockDto> stockDtos, Long reservationNumber) {
}
