package com.waitless.reservation.application.service.ticket;

import com.waitless.reservation.application.dto.TicketCreateServiceResponse;
import com.waitless.reservation.application.dto.TicketCreateServiceRequest;

public interface TicketService {
    TicketCreateServiceResponse createTicket(TicketCreateServiceRequest request);
}
