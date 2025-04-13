package com.waitless.reservation.application.mapper;

import com.waitless.reservation.domain.entity.ReservationMenu;
import com.waitless.reservation.presentation.dto.ReservationMenuResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMenuServiceMapper {
    ReservationMenuResponse toReservationMenuResponse(ReservationMenu menu);
}
