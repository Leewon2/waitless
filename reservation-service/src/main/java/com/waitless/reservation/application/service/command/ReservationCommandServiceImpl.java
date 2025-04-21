package com.waitless.reservation.application.service.command;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.dto.CancelMenuDto;
import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.application.dto.ReservationCreateResponse;
import com.waitless.reservation.application.event.dto.ReservationCompleteEvent;
import com.waitless.reservation.application.event.dto.ReservationVisitedEvent;
import com.waitless.reservation.application.mapper.ReservationServiceMapper;
import com.waitless.reservation.application.service.redis.RedisReservationQueueService;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.entity.ReservationMenu;
import com.waitless.reservation.domain.entity.ReservationStatus;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.infrastructure.adaptor.client.RestaurantClient;
import com.waitless.reservation.infrastructure.adaptor.client.dto.RestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationCommandServiceImpl implements ReservationCommandService {

    private final RedisStockService redisStockService;
    private final ReservationRepository reservationRepository;
    private final ReservationServiceMapper reservationServiceMapper;
    private final RedisReservationQueueService redisReservationQueueService;
    private final RestaurantClient restaurantClient;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public ReservationCreateResponse createReservation(ReservationCreateCommand reservationCreateCommand) {
        UUID storeId = reservationCreateCommand.restaurantId();
        Long reservationNumber = redisStockService.decrementStock(storeId, reservationCreateCommand.menus());

        List<ReservationMenu> menus = reservationCreateCommand.menus().stream()
                .map(m -> ReservationMenu.create(m.quantity(), m.price(), m.menuId(), m.menuName()))
                .toList();

        Reservation reservation = Reservation.create(
                storeId,
                reservationCreateCommand.restaurantName(),
                reservationCreateCommand.peopleCount(),
                reservationNumber,
                reservationCreateCommand.reservationDate(),
                ReservationStatus.WAITING,
                menus, reservationCreateCommand.userId()
        );

        reservationRepository.save(reservation);
        redisReservationQueueService.registerToWaitingQueue(reservation.getId(), reservation.getReservationDate(), reservation.getRestaurantId(), reservationNumber);

        eventPublisher.publishEvent(new ReservationCompleteEvent(reservationCreateCommand.userId(),reservation.getReservationNumber()));

        log.debug("예약 생성 :: Redis 재고 차감 및 DB 저장 완료: {}", storeId);

        return reservationServiceMapper.toReservationCreateResponse(reservation);
    }

    @Override
    public void cancelReservation(UUID reservationId) {
        Reservation findReservation = getReservationOrThrow(reservationId);

        List<CancelMenuDto> list = findReservation.getMenus().stream()
                .map(m -> new CancelMenuDto(m.getMenuId(), m.getQuantity()))
                .toList();

        redisStockService.incrementStock(findReservation.getRestaurantId(), list);
        redisReservationQueueService.removeFromWaitingQueue(
                findReservation.getId(),
                findReservation.getReservationDate(),
                findReservation.getRestaurantId()
        );

        findReservation.cancel(); // 예약 상태 CANCELLED 변경
    }

    @Override
    public void visitReservation(UUID reservationId) {
        Long userId = 1L; // TODO: 쓰레드로컬에서 추출 예정 -> 가게주인의 UserId
        Reservation findReservation = getReservationOrThrow(reservationId);

        RestaurantResponseDto restaurantResponseDto =
                restaurantClient.validateOwnerId(findReservation.getRestaurantId());

        if (!restaurantResponseDto.ownerId().equals(userId)) {
            throw BusinessException.from(ReservationErrorCode.RESTAURANT_OWNER_UNAUTHORIZED);
        }

        redisReservationQueueService.removeFromWaitingQueue(
                reservationId,
                findReservation.getReservationDate(),
                findReservation.getRestaurantId()
        );

        findReservation.visit(); // 상태값 방문완료 변경
        eventPublisher.publishEvent(new ReservationVisitedEvent(findReservation.getId(), findReservation.getRestaurantName(), userId));
    }

    private Reservation getReservationOrThrow(UUID reservationId) {
        return reservationRepository.findFetchById(reservationId)
                .orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND));
    }
}