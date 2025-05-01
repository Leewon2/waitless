package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.benefit.point.domain.vo.PointType;
import com.waitless.common.event.ReviewCreatedEvent;
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
public class ReviewCreatedEventHandler {

    private final PointCommandUseCase pointCommandUseCase;
    private static final int REWARD_POINT = 100;
    @KafkaListener(
            topics = "review-created-events",
            groupId = "point-service"
            )
    public void handleReviewCreatedEvent(
            @Payload ReviewCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(name = "traceId", required = false) String traceId
    ) {
        if (traceId != null) {
            MDC.put("traceId", traceId);
        }
        try {
            if (!"review-created".equals(key)) {
                log.debug("필터링된 메시지 key={}", key);
                return;
            }
            log.info("[Kafka] ReviewCreatedEvent 수신: {}, traceId={}", event, traceId);
            // 고의 예외 발생 로직
            if (event.getUserId() != null && event.getUserId() % 2 == 0) {
                throw new RuntimeException("고의 예외 발생: userId=" + event.getUserId());
            }
            PostPointCommand command = new PostPointCommand(
                    event.getUserId(),
                    event.getReviewId(),
                    event.getReservationId(),
                    REWARD_POINT,
                    PointType.Type.REVIEW_REWARD,
                    "[리뷰 작성 보상] 리뷰 ID: " + event.getReviewId()
            );
            pointCommandUseCase.createPoint(command);
            log.info("포인트 적립 완료: userId={}, reviewId={}, reservationId={}, point={}",
                    event.getUserId(), event.getReviewId(), event.getReservationId(), REWARD_POINT);
        } finally {
            MDC.remove("traceId");
        }
    }
}
