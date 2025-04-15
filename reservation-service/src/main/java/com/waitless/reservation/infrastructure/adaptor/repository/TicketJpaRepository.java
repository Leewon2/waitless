package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketRestaurant, UUID> {
}
