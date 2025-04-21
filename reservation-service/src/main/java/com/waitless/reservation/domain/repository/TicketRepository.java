package com.waitless.reservation.domain.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;

import java.time.LocalTime;
import java.util.List;

public interface TicketRepository {
    void save(TicketRestaurant ticketRestaurant);

    List<TicketRestaurant> findByOpenTime(LocalTime openTime);
}
