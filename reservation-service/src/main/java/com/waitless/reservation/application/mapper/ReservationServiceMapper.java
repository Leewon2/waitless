package com.waitless.reservation.application.mapper;

import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.presentation.dto.ReservationCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationServiceMapper {

    @Mapping(target = "reservationId", source = "id")
    ReservationCreateResponse toReservationCreateResponse(Reservation reservation);

}
