package com.waitless.message.infrastructure.db;

import com.waitless.message.domain.entity.SlackMessage;
import com.waitless.message.domain.repository.SlackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SlackRepositoryImpl implements SlackRepository {

    private final SlackJpaRepository slackJpaRepository;
    public SlackMessage save(SlackMessage slackMessage) {
        return slackJpaRepository.save(slackMessage);
    }

    @Override
    public Optional<SlackMessage> findById(UUID id) {
        return slackJpaRepository.findById(id);
    }
}
