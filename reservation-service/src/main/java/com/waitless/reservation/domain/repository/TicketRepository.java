package com.waitless.reservation.domain.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    void save(TicketRestaurant ticketRestaurant);

    List<TicketRestaurant> findByOpenTime(LocalTime openTime);

    List<TicketRestaurant> findByRestaurantId(UUID restaurantId);
}
