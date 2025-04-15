package com.waitless.reservation.presentation.controller.internal;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.dto.TicketCreateServiceResponse;
import com.waitless.reservation.application.service.ticket.TicketService;
import com.waitless.reservation.presentation.dto.TicketCreateRequest;
import com.waitless.reservation.presentation.mapper.TicketMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationInternalController {

    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @PostMapping("/ticket/app")
    public ResponseEntity create(@RequestBody @Valid TicketCreateRequest request) {
        TicketCreateServiceResponse ticket = ticketService.createTicket(ticketMapper.toTicketCreateServiceRequest(request));
        return ResponseEntity.ok(SingleResponse.success(ticket));
    }
}
