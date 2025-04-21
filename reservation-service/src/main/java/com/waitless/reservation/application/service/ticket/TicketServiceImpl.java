package com.waitless.reservation.application.service.ticket;

import com.waitless.reservation.application.dto.TicketCreateServiceRequest;
import com.waitless.reservation.application.dto.TicketCreateServiceResponse;
import com.waitless.reservation.domain.entity.TicketRestaurant;
import com.waitless.reservation.domain.repository.TicketRepository;
import com.waitless.reservation.infrastructure.adaptor.client.RestaurantClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final RestaurantClient restaurantClient;

    @Override
    public TicketCreateServiceResponse createTicket(TicketCreateServiceRequest request) {
        restaurantClient.existRestaurant(request.restaurantId());
        ticketRepository.save(TicketRestaurant.create(request.restaurantId(), request.openTime()));
        return new TicketCreateServiceResponse(request.restaurantId(), request.openTime());
    }
}
