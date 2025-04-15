package com.waitless.review.infrastructure.adaptor.in.messaging;

import com.waitless.common.command.CancelReviewCommand;
import com.waitless.common.event.PointIssuedFailedEvent;
import com.waitless.review.application.port.in.ReviewCommandUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        log.warn("[Kafka] 포인트 발급 실패 이벤트 수신: {}", event);
        CancelReviewCommand command = new CancelReviewCommand(
                event.getReviewId(),
                event.getUserId()
        );
        reviewCommandUseCase.cancelReview(command);
        log.info("보상 트랜잭션 실행 완료 → 리뷰 삭제 처리: reviewId={}, userId={}", event.getReviewId(), event.getUserId());
    }
}
