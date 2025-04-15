package com.waitless.benefit.point.infrastructure.adaptor.outbox.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.entity.PointOutboxMessage;
import com.waitless.benefit.point.infrastructure.adaptor.outbox.repository.JpaPointOutboxRepository;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.benefit.point.application.port.out.PointEventPublisher;
import com.waitless.common.event.PointIssuedFailedEvent;
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
        messages.addAll(outboxRepository.findByStatusAndRetryCountLessThan(PointOutboxMessage.OutboxStatus.FAILED, 5));

        for (PointOutboxMessage message : messages) {
            try {
                switch (message.getType()) {
                    case "point-issued" -> {
                        PointIssuedEvent event = objectMapper.readValue(message.getPayload(), PointIssuedEvent.class);
                        eventPublisher.publishPointIssued(event);
                        log.info("[Outbox] PointIssuedEvent 발행 완료: {}", message.getId());
                    }
                    case "point-issued-failed" -> {
                        PointIssuedFailedEvent event = objectMapper.readValue(message.getPayload(), PointIssuedFailedEvent.class);
                        eventPublisher.publishPointIssuedFailed(event);
                        log.info("[Outbox] PointIssuedFailedEvent 발행 완료: {}", message.getId());
                    }
                    default -> {
                        log.warn("[Outbox] 알 수 없는 이벤트 타입 처리 불가: {}", message.getType());
                        continue;
                    }
                }
                message.markAsSent();
                outboxRepository.save(message);
            } catch (Exception e) {
                message.incrementRetry();
                message.markAsFailed();
                outboxRepository.save(message);
                log.error("[Outbox] Kafka 발행 실패 (재시도 {}회): {}", message.getRetryCount(), message.getId(), e);
            }
        }
    }
}
