package com.waitless.reservation.domain.repository;

import com.waitless.reservation.domain.entity.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> findById(UUID id);

    Optional<Reservation> findFetchById(UUID id);
}
