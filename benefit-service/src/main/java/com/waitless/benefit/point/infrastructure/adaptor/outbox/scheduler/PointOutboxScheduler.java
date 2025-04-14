package com.waitless.benefit.point.infrastructure.adaptor.outbox.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.entity.PointOutboxMessage;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.repository.JpaPointOutboxRepository;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.benefit.point.application.port.out.PointEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointOutboxScheduler {

    private final JpaPointOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final PointEventPublisher eventPublisher;

    @Scheduled(fixedDelay = 10000)
    public void publishPendingMessages() {
        List<PointOutboxMessage> messages = outboxRepository.findByStatus(PointOutboxMessage.OutboxStatus.PENDING);

        for (PointOutboxMessage message : messages) {
            try {
                PointIssuedEvent event = objectMapper.readValue(message.getPayload(), PointIssuedEvent.class);
                eventPublisher.publishPointIssued(event);

                message.markAsSent();
                outboxRepository.save(message);
                log.info("[Outbox] PointIssuedEvent 발행 완료: {}", message.getId());
            } catch (Exception e) {
                log.error("[Outbox] Kafka 발행 실패: {}", message.getId(), e);
            }
        }
    }
}
