package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {
}
