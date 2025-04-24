package com.waitless.reservation.infrastructure.adaptor.repository;

import com.waitless.reservation.domain.entity.TicketRestaurant;
import com.waitless.reservation.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketJpaRepository jpaRepository;

    @Override
    public void save(TicketRestaurant ticketRestaurant) {
        jpaRepository.save(ticketRestaurant);
    }

    @Override
    public List<TicketRestaurant> findByOpenTime(LocalTime openTime) {
        return jpaRepository.findByOpenTime(openTime);
    }

    @Override
    public List<TicketRestaurant> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId);
    }
}
