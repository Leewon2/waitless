package com.waitless.review.infrastructure.adaptor.in.messaging;

import com.waitless.common.command.CancelReviewCommand;
import com.waitless.common.event.PointIssuedFailedEvent;
import com.waitless.review.application.port.in.ReviewCommandUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointIssuedFailedEventHandler {

    private final ReviewCommandUseCase reviewCommandUseCase;

    @KafkaListener(
            topics = "point-issued-failed-events",
            groupId = "review-service"
    )
    public void handlePointIssuedFailedEvent(
            @Payload PointIssuedFailedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(name = "traceId", required = false) String traceId
    ) {
        if (traceId != null) {
            MDC.put("traceId", traceId);
        }
        try {
            log.warn("[Kafka] 포인트 발급 실패 이벤트 수신: reviewId={}, userId={}, reservationId={}, traceId={}",
                    event.getReviewId(), event.getUserId(), event.getReservationId(), traceId);
            // 고의 예외 발생 로직
            if (event.getUserId() != null && event.getUserId() % 2 == 0) {
                throw new RuntimeException("고의 예외 발생: userId=" + event.getUserId());
            }
            CancelReviewCommand command = new CancelReviewCommand(
                    event.getReviewId(),
                    event.getUserId()
            );
            reviewCommandUseCase.cancelReview(command);
            log.info("보상 트랜잭션 실행 완료 → 리뷰 삭제 처리: reviewId={}, userId={}", event.getReviewId(), event.getUserId());
        } finally {
            MDC.remove("traceId");
        }
    }
}