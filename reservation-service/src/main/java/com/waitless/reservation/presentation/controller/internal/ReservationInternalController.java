package com.waitless.reservation.presentation.controller.internal;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.dto.ReservationServiceResponse;
import com.waitless.reservation.application.dto.TicketCreateServiceResponse;
import com.waitless.reservation.application.service.query.ReservationQueryService;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.application.service.ticket.TicketService;
import com.waitless.reservation.presentation.dto.ReservationServiceRequest;
import com.waitless.reservation.presentation.dto.TicketCreateRequest;
import com.waitless.reservation.presentation.mapper.TicketMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations/app")
public class ReservationInternalController {

    private final TicketMapper ticketMapper;
    private final TicketService ticketService;
    private final RedisStockService redisStockService;
    private final ReservationQueryService reservationQueryService;

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

    @PostMapping(path = "/api/v1/app/reservations/visited")
    @RequestMapping // 클래스의 @RequestMapping 무시
    public ResponseEntity<List<ReservationServiceResponse>> getVisitedReservations(
            @RequestBody ReservationServiceRequest request) {

        List<ReservationServiceResponse> result = reservationQueryService.findOneForReview(request.reservationId());
        return ResponseEntity.ok(result);
    }
}
