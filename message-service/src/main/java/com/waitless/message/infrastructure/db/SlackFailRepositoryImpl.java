package com.waitless.message.infrastructure.db;

import com.waitless.message.domain.entity.FailedSlackMessage;
import com.waitless.message.domain.repository.SlackFailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SlackFailRepositoryImpl implements SlackFailRepository {

    private final SlackFailJpaRepository slackFailJpaRepository;

    @Override
    public FailedSlackMessage save(FailedSlackMessage failedSlackMessage) {
        return slackFailJpaRepository.save(failedSlackMessage);
    }

    @Override
    public List<FailedSlackMessage> saveAll(List<FailedSlackMessage> failedSlackMessages) {
        return slackFailJpaRepository.saveAll(failedSlackMessages);
    }

    @Override
    public List<FailedSlackMessage> findAllByRetryCompletedFalseOrderByCreatedAtAsc() {
        return slackFailJpaRepository.findAllByRetryCompletedFalseOrderByCreatedAtAsc();
    }
}
