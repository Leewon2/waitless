package com.waitless.message.application.service;

import com.waitless.message.application.dto.SlackDeleteResponseDto;

import java.util.UUID;

public interface SlackService {
    void createSlack(String receiverId, Integer mySequence);

    SlackDeleteResponseDto deleteMessage(UUID id);
}
