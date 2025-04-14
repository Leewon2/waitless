package com.waitless.reservation.application.service.query;

import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import com.waitless.reservation.application.dto.ReservationSearchResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReservationQueryService {
    ReservationFindResponse findOne(UUID reservationId);

    Page<ReservationSearchResponse> search(ReservationSearchQuery reservationSearchQuery);
}
