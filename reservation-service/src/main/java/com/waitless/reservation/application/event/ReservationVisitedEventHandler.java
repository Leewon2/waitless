package com.waitless.reservation.application.event;

import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.response.SingleResponse;
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

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationVisited(ReservationVisitedEvent event) {
        try {
            String slackId = Optional.ofNullable(userClient.readUser(event.userId()))
                    .map(ResponseEntity::getBody)
                    .map(SingleResponse::getData)
                    .map(UserResponseDto::slackId)
                    .orElseThrow(() -> BusinessException.from(ReservationErrorCode.USER_NOT_FOUND));

            String message = messageService.buildVisitCompleteMessage(slackId, event.restaurantName(), event.userId());
            kafkaSlackEventProducer.sendReviewRequest(new ReviewRequestEvent(slackId, message));
        } catch (Exception e) {
            log.error("슬랙 메시지 전송 실패 :: 예약ID = {}, userId = {}", event.reservationId(), event.userId(), e);        }
    }
}