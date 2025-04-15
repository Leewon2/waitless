package com.waitless.reservation.presentation.mapper;

import com.waitless.reservation.application.dto.TicketCreateServiceRequest;
import com.waitless.reservation.presentation.dto.TicketCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketCreateServiceRequest toTicketCreateServiceRequest(TicketCreateRequest request);
}
