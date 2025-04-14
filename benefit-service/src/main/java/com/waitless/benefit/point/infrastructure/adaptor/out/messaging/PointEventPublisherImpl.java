package com.waitless.benefit.point.infrastructure.adaptor.out.messaging;

import com.waitless.benefit.point.application.port.out.PointEventPublisher;
import com.waitless.common.event.PointIssuedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointEventPublisherImpl implements PointEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "point-events";

    @Override
    public void publishPointIssued(PointIssuedEvent event) {
        kafkaTemplate.send(TOPIC, "point-issued", event);
        log.info("[Kafka] PointIssuedEvent 발행 완료: {}", event);
    }
}
