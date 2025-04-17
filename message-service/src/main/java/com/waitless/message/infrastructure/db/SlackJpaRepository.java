package com.waitless.message.infrastructure.db;

import com.waitless.message.domain.entity.SlackMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackJpaRepository extends JpaRepository<SlackMessage, UUID> {

}
