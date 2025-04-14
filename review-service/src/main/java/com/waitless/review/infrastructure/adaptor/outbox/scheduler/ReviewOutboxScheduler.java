package com.waitless.review.infrastructure.adaptor.outbox.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.review.application.port.out.ReviewEventPublisher;
import com.waitless.review.infrastructure.adaptor.outbox.entity.ReviewOutboxMessage;
import com.waitless.review.infrastructure.adaptor.outbox.repository.JpaReviewOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewOutboxScheduler {

    private final JpaReviewOutboxRepository outboxRepository;
    private final ReviewEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 10000) // 10초마다 실행
    public void publishPendingOutboxMessages() {
        List<ReviewOutboxMessage> messages = outboxRepository.findByStatus(ReviewOutboxMessage.OutboxStatus.PENDING);
        for (ReviewOutboxMessage message : messages) {
            try {
                ReviewCreatedEvent event = objectMapper.readValue(message.getPayload(), ReviewCreatedEvent.class);
                eventPublisher.publishReviewCreated(event);

                message.markAsSent();
                outboxRepository.save(message);
                log.info("[Outbox] ReviewCreatedEvent 발행 완료: {}", message.getId());
            } catch (Exception e) {
                log.error("[Outbox] Kafka 발행 실패: {}", message.getId(), e);
            }
        }
    }
}
