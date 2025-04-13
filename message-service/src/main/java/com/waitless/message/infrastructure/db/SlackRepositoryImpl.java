package com.waitless.message.infrastructure.db;

import com.waitless.message.domain.entity.SlackMessage;
import com.waitless.message.domain.repository.SlackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SlackRepositoryImpl implements SlackRepository {

    private final SlackJpaRepository slackJpaRepository;
    public void save(SlackMessage slackMessage) {
        slackJpaRepository.save(slackMessage);
    }
}
