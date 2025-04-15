package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.benefit.point.domain.vo.PointType;
import com.waitless.common.event.ReviewCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewCreatedEventHandler {

    private final PointCommandUseCase pointCommandUseCase;

    private static final int REWARD_POINT = 100;

    @KafkaListener(
            topics = "review-events",
            groupId = "point-service",
            properties = {"spring.json.value.default.type=com.waitless.common.event.ReviewCreatedEvent"
            })
    public void handleReviewCreatedEvent(ReviewCreatedEvent event) {
        log.info("[Kafka] ReviewCreatedEvent 수신: {}", event);

        PostPointCommand command = new PostPointCommand(
                event.getUserId(),
                event.getReviewId(),
                REWARD_POINT,
                PointType.Type.REVIEW_REWARD,
                "[리뷰 작성 보상] 리뷰 ID: " + event.getReviewId()
        );
        pointCommandUseCase.createPoint(command);
        log.info("포인트 적립 완료: userId={}, reviewId={}, point={}", event.getUserId(), event.getReviewId(), REWARD_POINT);
    }
}
