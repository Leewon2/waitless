package com.waitless.review.application.port.out;

import com.waitless.review.application.dto.client.VisitedReservationRequestDto;
import com.waitless.review.application.dto.client.VisitedReservationResponseDto;
import java.util.List;

public interface VisitedReservationPort {
    List<VisitedReservationResponseDto> getVisitedReservations(VisitedReservationRequestDto requestDto);
}
