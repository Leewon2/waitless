package com.waitless.review.infrastructure.adaptor.out.client;

import com.waitless.review.application.port.out.VisitedReservationPort;
import com.waitless.review.application.dto.client.VisitedReservationRequestDto;
import com.waitless.review.application.dto.client.VisitedReservationResponseDto;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceRequest;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceResponse;
import com.waitless.review.infrastructure.adaptor.out.client.mapper.VisitedReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitedReservationClientAdapter implements VisitedReservationPort {

    private final ReservationInternalClient reservationInternalClient;
    private final VisitedReservationMapper mapper;
    @Override
    public List<VisitedReservationResponseDto> getVisitedReservations(VisitedReservationRequestDto requestDto) {
        ReservationServiceRequest infraRequest = mapper.toInfraRequest(requestDto);
        List<ReservationServiceResponse> response = reservationInternalClient.getVisitedReservations(infraRequest);
        return response.stream()
                .map(mapper::toApplicationResponse)
                .toList();
    }
}
