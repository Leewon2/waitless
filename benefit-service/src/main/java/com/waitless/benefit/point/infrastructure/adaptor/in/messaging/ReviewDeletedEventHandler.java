package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.common.event.ReviewDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDeletedEventHandler {

    private final PointCommandUseCase pointCommandUseCase;

    @KafkaListener(
            topics = "review-events",
            groupId = "point-service",
            properties = {
                    "spring.json.value.default.type=com.waitless.common.event.ReviewDeletedEvent"
            }
    )
    public void handleReviewDeletedEvent(ReviewDeletedEvent event) {
        log.info("[Kafka] ReviewDeletedEvent 수신: {}", event);
        pointCommandUseCase.deletePointByReview(event.getReviewId(), event.getUserId());
        log.info("포인트 삭제 완료: reviewId={}", event.getReviewId());
    }
}