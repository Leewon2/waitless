package com.waitless.reservation.presentation.mapper;

import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.presentation.dto.ReservationCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationCommandMapper {
    ReservationCreateCommand toCommand(ReservationCreateRequest reservationCreateRequest);


}