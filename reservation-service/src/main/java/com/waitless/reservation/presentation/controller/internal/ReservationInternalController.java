package com.waitless.reservation.presentation.controller.internal;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.dto.TicketCreateServiceResponse;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.application.service.ticket.TicketService;
import com.waitless.reservation.presentation.dto.TicketCreateRequest;
import com.waitless.reservation.presentation.mapper.TicketMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation/app")
public class ReservationInternalController {

    private final TicketMapper ticketMapper;
    private final TicketService ticketService;
    private final RedisStockService redisStockService;

    @PostMapping("/ticket")
    public ResponseEntity create(@RequestBody @Valid TicketCreateRequest request) {
        TicketCreateServiceResponse ticket = ticketService.createTicket(ticketMapper.toTicketCreateServiceRequest(request));
        return ResponseEntity.ok(SingleResponse.success(ticket));
    }

    @PostMapping("/{restaurantId}/closed")
    public ResponseEntity closed(@PathVariable UUID restaurantId) {
        redisStockService.closedRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }
}
