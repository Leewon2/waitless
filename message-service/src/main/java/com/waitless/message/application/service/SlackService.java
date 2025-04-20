package com.waitless.message.application.service;

import com.waitless.message.application.dto.SlackDeleteResponseDto;
import com.waitless.message.domain.entity.SlackMessage;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SlackService {
    CompletableFuture<SlackMessage> createSlack(String receiverId, Integer mySequence);

    SlackDeleteResponseDto deleteMessage(UUID id);

    void createReviewRequestSlack(String slackId, String message);
}
