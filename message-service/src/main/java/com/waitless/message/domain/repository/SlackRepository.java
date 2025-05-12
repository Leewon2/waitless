package com.waitless.message.domain.repository;

import com.waitless.message.domain.entity.SlackMessage;

import java.util.Optional;
import java.util.UUID;

public interface SlackRepository {
    SlackMessage save(SlackMessage slackMessage);

    Optional<SlackMessage> findById(UUID id);
}
