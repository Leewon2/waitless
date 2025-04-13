package com.waitless.benefit.point.infrastructure.adaptor.in.messaging;

import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.repository.PointRepository;
import com.waitless.benefit.point.domain.vo.PointAmount;
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

    private final PointRepository pointRepository;

    private static final int REWARD_POINT = 100;

    @KafkaListener(topics = "review-events", groupId = "point-service", properties = {
            "spring.json.value.default.type=com.waitless.common.event.ReviewCreatedEvent"
    })
    public void handleReviewCreatedEvent(ReviewCreatedEvent event) {
        log.info("[Kafka] ReviewCreatedEvent 수신: {}", event);

        // 포인트 생성
        Point point = Point.of(
                event.getUserId(),
                PointAmount.of(REWARD_POINT),
                PointType.of(PointType.Type.REVIEW_REWARD),
                "[리뷰 작성 보상] 리뷰 ID: " + event.getReviewId()
        );

        pointRepository.save(point);
        log.info("포인트 적립 완료: userId={}, point={}", point.getUserId(), point.getAmount().getPointValue());
    }
}
