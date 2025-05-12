package com.waitless.review.infrastructure.adaptor.out.client.mapper;

import com.waitless.review.application.dto.client.VisitedReservationRequestDto;
import com.waitless.review.application.dto.client.VisitedReservationResponseDto;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceRequest;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitedReservationMapper {
    ReservationServiceRequest toInfraRequest(VisitedReservationRequestDto requestDto);
    VisitedReservationResponseDto toApplicationResponse(ReservationServiceResponse responseDto);
}
