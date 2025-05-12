package com.waitless.message.domain.repository;

import com.waitless.message.domain.entity.FailedSlackMessage;

import java.util.List;

public interface SlackFailRepository {
    FailedSlackMessage save(FailedSlackMessage failedSlackMessage);

    List<FailedSlackMessage> saveAll(List<FailedSlackMessage> failedSlackMessages);

    List<FailedSlackMessage> findAllByRetryCompletedFalseOrderByCreatedAtAsc();
}
