package com.waitless.reservation.domain.repository;

import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;

public interface ReservationQueryRepository {
    Page<Reservation> findByCustomCondition(ReservationSearchQuery reservationSearchQuery);
}