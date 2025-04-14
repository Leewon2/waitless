package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public void save(Reservation reservation) {
        reservationJpaRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return reservationJpaRepository.findById(id);
    }

    @Override
    public Optional<Reservation> findFetchById(UUID id) {
        return reservationJpaRepository.findFetchById(id);
    }
}
