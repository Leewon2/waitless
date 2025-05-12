package com.waitless.reservation.application.service.query;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.dto.ReservationCurrentResponse;
import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.application.dto.ReservationServiceResponse;
import com.waitless.reservation.application.mapper.ReservationServiceMapper;
import com.waitless.reservation.application.service.redis.RedisReservationQueueService;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import com.waitless.reservation.application.dto.ReservationSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ReservationRepository reservationRepository;
    private final ReservationServiceMapper reservationServiceMapper;
    private final RedisReservationQueueService queueService;

    @Override
//    @Cacheable(value = "cache:reservationFindOne", key = "#reservationId")
    public ReservationFindResponse findOne(UUID reservationId) {
        Reservation findReservation = reservationRepository.findFetchById(reservationId).orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND));
        return reservationServiceMapper.toReservationFindResponse(findReservation);
    }

    @Override
    public Page<ReservationSearchResponse> search(ReservationSearchQuery reservationSearchQuery) {
        Page<Reservation> Reservations = reservationRepository.findByCustomCondition(reservationSearchQuery);
        return Reservations.map(m -> new ReservationSearchResponse(m.getId(), m.getRestaurantName(),
                m.getRestaurantId(), m.getReservationNumber(), m.getPeopleCount(), m.getUserId()));
    }

    @Override
    public ReservationCurrentResponse currentNumber(UUID reservationId) {
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND));
        findReservation.validateWaitingStatus();
        return ReservationCurrentResponse.of(findReservation, queueService.findCurrentNumberFromWaitingQueue(reservationId, findReservation.getRestaurantId()));
    }

    @Override
    public List<ReservationServiceResponse> findOneForReview(UUID reservationId) {
        return List.of(
                reservationRepository.findReviewById(reservationId)
                        .orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND))
        );
    }
}
