package com.waitless.reservation.application.mapper;

import com.waitless.reservation.application.dto.ReservationCreateResponse;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReservationMenuServiceMapper.class)
public interface ReservationServiceMapper {

    @Mapping(target = "reservationId", source = "id")
    ReservationCreateResponse toReservationCreateResponse(Reservation reservation);

    @Mapping(target = "reservationId", source = "id")
    @Mapping(target = "totalPrice", source = "totalPrice")
    ReservationFindResponse toReservationFindResponse(Reservation reservation);
}
