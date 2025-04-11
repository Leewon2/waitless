package com.waitless.reservation.application.service.command;

import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.application.mapper.ReservationServiceMapper;
import com.waitless.reservation.application.service.redis.RedisReservationService;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.entity.ReservationMenu;
import com.waitless.reservation.domain.entity.ReservationStatus;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.presentation.dto.ReservationCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RedisReservationService redisReservationService;

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
        redisReservationService.registerToWaitingQueue(reservation.getId(), reservation.getReservationDate(), reservation.getRestaurantId(), reservationNumber);



        /**
         * todo: 카프카 메시지 발행
         */

        log.debug("예약 생성 :: Redis 재고 차감 및 DB 저장 완료: {}", storeId);

        return reservationServiceMapper.toReservationCreateResponse(reservation);
    }
}