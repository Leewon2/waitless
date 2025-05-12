package com.waitless.benefit.point.infrastructure.adaptor.outbox.repository;

import com.waitless.benefit.point.infrastructure.adaptor.outbox.entity.PointOutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPointOutboxRepository extends JpaRepository<PointOutboxMessage, UUID> {
    List<PointOutboxMessage> findByStatus(PointOutboxMessage.OutboxStatus status);
    List<PointOutboxMessage> findByStatusAndRetryCountLessThan(PointOutboxMessage.OutboxStatus status, int maxRetry);
}
