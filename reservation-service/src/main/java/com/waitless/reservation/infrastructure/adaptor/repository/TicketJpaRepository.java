package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketRestaurant, UUID> {
    List<TicketRestaurant> findByOpenTime(LocalTime openTime);

    List<TicketRestaurant> findByRestaurantId(UUID restaurantId);

}
