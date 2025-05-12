package com.waitless.reservation.application.service.query;

import com.waitless.reservation.application.dto.ReservationCurrentResponse;
import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.application.dto.ReservationServiceResponse;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import com.waitless.reservation.application.dto.ReservationSearchResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ReservationQueryService {
    ReservationFindResponse findOne(UUID reservationId);

    Page<ReservationSearchResponse> search(ReservationSearchQuery reservationSearchQuery);

    ReservationCurrentResponse currentNumber(UUID reservationId);

    List<ReservationServiceResponse> findOneForReview(UUID reservationId);
}
