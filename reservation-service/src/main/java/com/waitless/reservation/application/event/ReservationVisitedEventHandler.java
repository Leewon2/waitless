package com.waitless.reservation.application.event;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.event.dto.CompletedReservationEvent;
import com.waitless.reservation.application.event.dto.ReservationCompleteEvent;
import com.waitless.reservation.application.event.dto.ReservationVisitedEvent;
import com.waitless.reservation.application.event.dto.ReviewRequestEvent;
import com.waitless.reservation.application.service.message.MessageService;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.infrastructure.adaptor.client.UserClient;
import com.waitless.reservation.infrastructure.adaptor.client.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class ReservationVisitedEventHandler {

    private final UserClient userClient;
    private final MessageService messageService;
    private final KafkaSlackEventProducer kafkaSlackEventProducer;
    private final KafkaCompletedReservationEventProducer kafkaCompletedReservationEventProducer;

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationVisited(ReservationVisitedEvent event) {
        try {
            // TODO : 주석 삭제
            String slackId = getSlackId(event.userId());

            String message = messageService.buildVisitCompleteMessage(slackId, event.restaurantName(), event.userId());
            kafkaSlackEventProducer.sendReviewRequest(new ReviewRequestEvent(slackId, message));
        } catch (Exception e) {
            log.error("슬랙 메시지 전송 실패 :: 예약ID = {}, userId = {}", event.reservationId(), event.userId(), e);        }
    }

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCompletedReservation(ReservationCompleteEvent event) {
        try {
            String slackId = getSlackId(event.userId());
            kafkaCompletedReservationEventProducer.sendCompletedReservation(new CompletedReservationEvent(slackId,event.sequence().intValue()));
        } catch (Exception e) {
            log.error("슬랙 메시지 전송 실패 :: 순번 = {}, userId = {}", event.sequence(), event.userId(), e);        }
    }

    private String getSlackId(Long userId) {
        return Optional.ofNullable(userClient.readUser(userId))
                .map(ResponseEntity::getBody)
                .map(SingleResponse::getData)
                .map(UserResponseDto::slackId)
                .orElseThrow(() -> BusinessException.from(ReservationErrorCode.USER_NOT_FOUND));
    }
}