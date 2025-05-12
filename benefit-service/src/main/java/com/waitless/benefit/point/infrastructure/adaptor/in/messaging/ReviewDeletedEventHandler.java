package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.common.event.ReviewDeletedEvent;
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
public class ReviewDeletedEventHandler {

    private final PointCommandUseCase pointCommandUseCase;
    @KafkaListener(
            topics = "review-deleted-events",
            groupId = "point-service"
    )
    public void handleReviewDeletedEvent(
            @Payload ReviewDeletedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(name = "traceId", required = false) String traceId
    ) {
        if (traceId != null) {
            MDC.put("traceId", traceId);
        }
        try {
            if (!"review-deleted".equals(key)) {
                log.debug("필터링된 메시지 key={}", key);
                return;
            }
            log.info("[Kafka] ReviewDeletedEvent 수신: {}, traceId={}", event, traceId);
            // 고의 예외 발생 로직
            if (event.getUserId() != null && event.getUserId() % 2 == 0) {
                throw new RuntimeException("고의 예외 발생: userId=" + event.getUserId());
            }
            pointCommandUseCase.deletePointByReview(event.getReviewId(), event.getUserId());
            log.info("포인트 삭제 완료: reviewId={}, traceId={}", event.getReviewId(), traceId);
        } finally {
            MDC.remove("traceId");
        }
    }
}