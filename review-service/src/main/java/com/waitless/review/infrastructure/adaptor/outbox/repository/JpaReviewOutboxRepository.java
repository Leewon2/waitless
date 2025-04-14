package com.waitless.review.infrastructure.adaptor.outbox.repository;

import com.waitless.review.infrastructure.adaptor.outbox.entity.ReviewOutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaReviewOutboxRepository extends JpaRepository<ReviewOutboxMessage, UUID> {
    List<ReviewOutboxMessage> findByStatus(ReviewOutboxMessage.OutboxStatus status);
}
