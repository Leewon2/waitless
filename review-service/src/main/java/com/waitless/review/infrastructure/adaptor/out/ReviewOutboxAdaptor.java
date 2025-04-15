package com.waitless.review.infrastructure.adaptor.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;
import com.waitless.review.application.port.out.ReviewOutboxPort;
import com.waitless.review.infrastructure.adaptor.outbox.entity.ReviewOutboxMessage;
import com.waitless.review.infrastructure.adaptor.outbox.repository.JpaReviewOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReviewOutboxAdaptor implements ReviewOutboxPort {

    private final ObjectMapper objectMapper;
    private final JpaReviewOutboxRepository reviewOutboxRepository;

    @Override
    public void saveReviewCreatedEvent(ReviewCreatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            ReviewOutboxMessage message = ReviewOutboxMessage.builder()
                    .aggregateType("REVIEW")
                    .type("review-created")
                    .payload(payload)
                    .status(ReviewOutboxMessage.OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
            reviewOutboxRepository.save(message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox 직렬화 실패", e);
        }
    }
    @Override
    public void saveReviewDeletedEvent(ReviewDeletedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            ReviewOutboxMessage message = ReviewOutboxMessage.builder()
                    .aggregateType("REVIEW")
                    .type("review-deleted")
                    .payload(payload)
                    .status(ReviewOutboxMessage.OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            reviewOutboxRepository.save(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Outbox 직렬화 실패 [review-deleted]", e);
        }
    }
}
