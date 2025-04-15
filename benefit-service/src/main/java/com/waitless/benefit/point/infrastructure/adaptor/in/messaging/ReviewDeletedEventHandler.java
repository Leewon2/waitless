package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.common.event.ReviewDeletedEvent;
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
public class ReviewDeletedEventHandler {

    private final PointCommandUseCase pointCommandUseCase;

    @KafkaListener(
            topics = "review-deleted-events",
            groupId = "point-service"
    )
    public void handleReviewDeletedEvent(
            @Payload ReviewDeletedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        if (!"review-deleted".equals(key)) return;

        log.info("[Kafka] ReviewDeletedEvent 수신: {}", event);
        pointCommandUseCase.deletePointByReview(event.getReviewId(), event.getUserId());
        log.info("포인트 삭제 완료: reviewId={}", event.getReviewId());
    }
}