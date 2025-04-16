package com.waitless.message.infrastructure.db;

import com.waitless.message.domain.entity.FailedSlackMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SlackFailJpaRepository extends JpaRepository<FailedSlackMessage, UUID> {
    List<FailedSlackMessage> findAllByRetryCompletedFalseOrderByCreatedAtAsc();
}
