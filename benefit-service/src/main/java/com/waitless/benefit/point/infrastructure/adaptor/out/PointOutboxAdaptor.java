package com.waitless.benefit.point.infrastructure.adaptor.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.benefit.point.application.port.out.PointOutboxPort;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.entity.PointOutboxMessage;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.repository.JpaPointOutboxRepository;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PointOutboxAdaptor implements PointOutboxPort {

    private final ObjectMapper objectMapper;
    private final JpaPointOutboxRepository outboxRepository;

    @Override
    public void savePointIssuedEvent(PointIssuedEvent event) {
        saveEvent(event, "point-issued");
    }

    @Override
    public void savePointIssuedFailedEvent(PointIssuedFailedEvent event) {
        saveEvent(event, "point-issued-failed");
    }

    private void saveEvent(Object event, String type) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            PointOutboxMessage message = PointOutboxMessage.builder()
                    .aggregateType("POINT")
                    .type(type)
                    .payload(payload)
                    .status(PointOutboxMessage.OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxRepository.save(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox 직렬화 실패 [" + type + "]", e);
        }
    }
}
