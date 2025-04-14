package com.waitless.reservation.application.service.query;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.mapper.ReservationServiceMapper;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ReservationRepository reservationRepository;
    private final ReservationServiceMapper reservationServiceMapper;

    @Override
//    @Cacheable(value = "cache:reservationFindOne", key = "#reservationId")
    public ReservationFindResponse findOne(UUID reservationId) {
        Reservation findReservation = reservationRepository.findFetchById(reservationId).orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND));
        return reservationServiceMapper.toReservationFindResponse(findReservation);
    }
}
