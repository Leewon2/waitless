package com.waitless.reservation.domain.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;

public interface TicketRepository {
    void save(TicketRestaurant ticketRestaurant);
}
