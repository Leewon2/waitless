package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

    @EntityGraph(attributePaths = "menus")
    Optional<Reservation> findFetchById(@Param("id") UUID id);
}
