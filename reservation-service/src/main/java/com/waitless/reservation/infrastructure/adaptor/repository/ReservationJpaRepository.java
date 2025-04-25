package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.application.dto.ReservationServiceResponse;
import com.waitless.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, UUID> {

    @EntityGraph(attributePaths = "menus")
    Optional<Reservation> findFetchById(@Param("id") UUID id);

    @Query("""
                SELECT new com.waitless.reservation.application.dto.ReservationServiceResponse(
                    r.id,
                    r.userId,
                    r.restaurantId
                )
                FROM Reservation r
                WHERE r.id = :reservationId AND r.status = 'COMPLETED'
            """)
    Optional<ReservationServiceResponse> findReviewById(@Param("reservationId") UUID reservationId);

}
