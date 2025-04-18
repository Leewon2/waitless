package com.waitless.reservation.application.event.dto;

public record ReviewRequestEvent(
        String slackId,
        String message
) {}