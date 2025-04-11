package com.waitless.message.application.service;

import com.waitless.message.application.dto.SlackDeleteResponseDto;
import com.waitless.message.domain.entity.SlackMessage;

import java.util.UUID;

public interface SlackService {
    SlackMessage createSlack(String receiverId, Integer mySequence);

    SlackDeleteResponseDto deleteMessage(UUID id);
}
