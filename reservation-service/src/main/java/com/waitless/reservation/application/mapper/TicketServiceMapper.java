package com.waitless.reservation.application.mapper;

import com.waitless.reservation.application.dto.TicketCreateServiceRequest;
import com.waitless.reservation.domain.entity.TicketRestaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketServiceMapper {
//    TicketRestaurant toTicketRestaurant(TicketCreateServiceRequest request);
}
