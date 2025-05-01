package com.waitless.benefit.point.infrastructure.adaptor.out.messaging;

import com.waitless.benefit.point.application.port.out.PointEventPublisher;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;
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
public class PointEventPublisherImpl implements PointEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_ISSUED = "point-issued-events";
    private static final String TOPIC_FAILED = "point-issued-failed-events";

    @Override
    public void publishPointIssued(PointIssuedEvent event) {
        String traceId = MDC.get("traceId");
        ProducerRecord<String, Object> record = new ProducerRecord<>(TOPIC_ISSUED, "point-issued", event);
        if (traceId != null) {
            record.headers().add(new RecordHeader("traceId", traceId.getBytes(StandardCharsets.UTF_8)));
        }
        kafkaTemplate.send(record);
        log.info("[Kafka] PointIssuedEvent 발행 완료: {}, traceId={}", event, traceId);
    }

    @Override
    public void publishPointIssuedFailed(PointIssuedFailedEvent event) {
        String traceId = MDC.get("traceId");
        ProducerRecord<String, Object> record = new ProducerRecord<>(TOPIC_FAILED, "point-issued-failed", event);
        if (traceId != null) {
            record.headers().add(new RecordHeader("traceId", traceId.getBytes(StandardCharsets.UTF_8)));
        }
        kafkaTemplate.send(record);
        log.info("[Kafka] PointIssuedFailedEvent 발행 완료: {}, traceId={}", event, traceId);
    }

}
