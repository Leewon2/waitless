package com.waitless.benefit.point.infrastructure.adaptor.out.messaging;

import com.waitless.benefit.point.application.port.out.PointEventPublisher;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointEventPublisherImpl implements PointEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_ISSUED = "point-issued-events";
    private static final String TOPIC_FAILED = "point-issued-failed-events";

    @Override
    public void publishPointIssued(PointIssuedEvent event) {
        kafkaTemplate.send(TOPIC_ISSUED, "point-issued", event);
        log.info("[Kafka] PointIssuedEvent 발행 완료: {}", event);
    }

    @Override
    public void publishPointIssuedFailed(PointIssuedFailedEvent event) {
        kafkaTemplate.send(TOPIC_FAILED, "point-issued-failed", event);
        log.info("[Kafka] PointIssuedFailedEvent 발행 완료: {}", event);
    }

}
