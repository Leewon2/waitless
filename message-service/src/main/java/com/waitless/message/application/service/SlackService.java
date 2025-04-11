package com.waitless.message.application.service;

public interface SlackService {
    void createSlack(String receiverId, Integer mySequence);
}
