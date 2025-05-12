package com.waitless.reservation.application.event;

import com.waitless.common.dto.ReservationCancelEvent;
import com.waitless.common.event.VisitRequestMessageEvent;
import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.event.dto.*;
import com.waitless.reservation.application.service.message.MessageService;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.infrastructure.adaptor.client.UserClient;
import com.waitless.reservation.infrastructure.adaptor.client.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class ReservationMessageEventHandler {

    private final UserClient userClient;
    private final MessageService messageService;
    private final KafkaSlackEventProducer kafkaSlackEventProducer;
    private final KafkaCompletedReservationEventProducer kafkaCompletedReservationEventProducer;
    private final StringRedisTemplate redisTemplate;
    private final ReservationRepository reservationRepository;

    private static final String QUEUE_PREFIX = "reservation:queue:";


    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationVisited(ReservationVisitedEvent event) {
        try {
            // TODO : 주석 삭제
            String slackId = getSlackId(event.userId());

            String message = messageService.buildVisitCompleteMessage(slackId, event.restaurantName(), event.userId());
            kafkaSlackEventProducer.sendReviewRequest(new ReviewRequestEvent(slackId, message));
        } catch (Exception e) {
            log.error("슬랙 메시지 전송 실패 :: 예약ID = {}, userId = {}", event.reservationId(), event.userId(), e);
        }
    }

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCompletedReservation(ReservationCompleteEvent event) {
        try {
            String slackId = getSlackId(event.userId());
            kafkaCompletedReservationEventProducer.sendCompletedReservation(new CompletedReservationEvent(slackId, event.sequence().intValue()));
        } catch (Exception e) {
            log.error("예약 완료 슬랙 메시지 전송 실패 :: 순번 = {}, userId = {}", event.sequence(), event.userId(), e);
        }
    }

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCancelReservation(ReservationCancelRequestEvent event) {
        try {
            String slackId = getSlackId(event.userId());
            String message = messageService.buildCancelMessage(event.restaurantName());
            kafkaSlackEventProducer.sendCancelRequest(new ReservationCancelEvent(slackId, message));
        } catch (Exception e) {
            log.error("취소 슬랙 메시지 전송 실패 :: userid = {}", event.userId(), e);
        }
    }

    @Async("messageExecutor")
    @EventListener
    @Transactional(readOnly = true)
    public void handleVisitRequestReservation(VisitRequestEvent event) {
        UUID restaurantId = event.restaurantId();
        String zsetKey = QUEUE_PREFIX + restaurantId;
        String alertKey = "reservation:alerted:" + restaurantId;

        try {
            String topReservationId = redisTemplate.opsForZSet()
                    .range(zsetKey, 0, 0)
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (topReservationId == null) {
                log.info("[Queue] 식당 {}: 대기열 비어있음", restaurantId);
                return;
            }

            // 이미 알림 보낸 예약인지 확인
            String lastAlertedId = redisTemplate.opsForValue().get(alertKey);
            if (topReservationId.equals(lastAlertedId)) {
                log.info("[Queue] 식당 {}: 예약 {}는 이미 알림 전송됨", restaurantId, topReservationId);
                return;
            }

            // 예약 조회
            Reservation reservation = reservationRepository.findById(UUID.fromString(topReservationId))
                    .orElseThrow(() -> BusinessException.from(ReservationErrorCode.RESERVATION_NOT_FOUND));

            // 사용자 Slack ID 조회 및 메시지 발송
//            String slackId = getSlackId(reservation.getUserId());
            String slackId = "asdasd";
            String message = messageService.buildVisitRequestMessage(reservation.getRestaurantName());
            kafkaSlackEventProducer.sendVisitRequest(new VisitRequestMessageEvent(slackId, message));

            // 알림 보낸 예약 ID 기록
            redisTemplate.opsForValue().set(alertKey, topReservationId);
            log.info("[Queue] 식당 {}: 예약 {}에게 입장 요청 메시지 전송 완료", restaurantId, topReservationId);
        } catch (Exception e) {
            log.error("[Queue] 식당 {}: 1등 메시지 전송 실패", restaurantId, e);
        }
    }

    private String getSlackId(Long userId) {
        return Optional.ofNullable(userClient.readUser(userId))
                .map(ResponseEntity::getBody)
                .map(SingleResponse::getData)
                .map(UserResponseDto::slackId)
                .orElseThrow(() -> BusinessException.from(ReservationErrorCode.USER_NOT_FOUND));
    }
}