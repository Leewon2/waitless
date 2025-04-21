package com.waitless.message.application.event.dto;

public record ReviewRequestEvent(
        String slackId,
        String message
) {}