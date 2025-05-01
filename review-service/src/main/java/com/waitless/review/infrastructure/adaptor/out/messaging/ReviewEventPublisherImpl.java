package com.waitless.review.infrastructure.adaptor.out.messaging;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;
import com.waitless.review.application.port.out.ReviewEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventPublisherImpl implements ReviewEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_REVIEW_CREATED = "review-created-events";
    private static final String TOPIC_REVIEW_DELETED = "review-deleted-events";

    @Override
    public void publishReviewCreated(ReviewCreatedEvent event) {
        String traceId = MDC.get("traceId");
        ProducerRecord<String, Object> record = new ProducerRecord<>(TOPIC_REVIEW_CREATED, "review-created", event);
        if (traceId != null) {
            record.headers().add(new RecordHeader("traceId", traceId.getBytes(StandardCharsets.UTF_8)));
        }
        kafkaTemplate.send(record);
        log.info("[Kafka] 발행 → topic={} key={} payload={} traceId={}", TOPIC_REVIEW_CREATED, "review-created", event, traceId);
    }

    @Override
    public void publishReviewDeleted(ReviewDeletedEvent event) {
        String traceId = MDC.get("traceId");
        ProducerRecord<String, Object> record = new ProducerRecord<>(TOPIC_REVIEW_DELETED, "review-deleted", event);
        if (traceId != null) {
            record.headers().add(new RecordHeader("traceId", traceId.getBytes(StandardCharsets.UTF_8)));
        }
        kafkaTemplate.send(record);
        log.info("[Kafka] 발행 → topic={} key={} payload={} traceId={}", TOPIC_REVIEW_DELETED, "review-deleted", event, traceId);
    }
}