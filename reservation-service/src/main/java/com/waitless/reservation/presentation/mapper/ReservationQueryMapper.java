package com.waitless.reservation.presentation.mapper;

import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.presentation.dto.ReservationSearchRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationQueryMapper {
    ReservationSearchQuery toReservationSearchQuery(ReservationSearchRequest reservationSearchRequest);
}
