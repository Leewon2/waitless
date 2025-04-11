package com.waitless.message.domain.repository;

import com.waitless.message.domain.entity.SlackMessage;

public interface SlackRepository {
    void save(SlackMessage slackMessage);
}
