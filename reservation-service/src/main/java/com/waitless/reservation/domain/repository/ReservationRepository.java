package com.waitless.reservation.domain.repository;

import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.application.dto.ReservationServiceResponse;
import com.waitless.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> findById(UUID id);

    Optional<Reservation> findFetchById(UUID id);

    Page<Reservation> findByCustomCondition(ReservationSearchQuery reservationSearchQuery);

    Optional<ReservationServiceResponse> findReviewById(UUID reservationId);
}
