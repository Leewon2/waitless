package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;
import com.waitless.reservation.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketJpaRepository jpaRepository;

    @Override
    public void save(TicketRestaurant ticketRestaurant) {
        jpaRepository.save(ticketRestaurant);
    }
}
