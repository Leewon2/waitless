package com.waitless.message.application.event.dto;

public record CompletedReservationEvent(        String slackId,
                                                Integer sequence) {
}
