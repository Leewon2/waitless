package com.waitless.review.infrastructure.adaptor.out.messaging;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;
import com.waitless.review.application.port.out.ReviewEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventPublisherImpl implements ReviewEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_REVIEW_CREATED = "review-events";
    private static final String TOPIC_REVIEW_DELETED = "review-events";

    @Override
    public void publishReviewCreated(ReviewCreatedEvent event) {
        kafkaTemplate.send(TOPIC_REVIEW_CREATED, "review-created", event);
        log.info("[Kafka] ReviewCreatedEvent 발행 완료: {}", event);
    }

    @Override
    public void publishReviewDeleted(ReviewDeletedEvent event) {
        kafkaTemplate.send(TOPIC_REVIEW_DELETED, "review-deleted", event);
        log.info("[Kafka] ReviewDeletedEvent 발행 완료: {}", event);
    }
}
