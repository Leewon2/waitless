package com.waitless.reservation.application.event.dto;

public record CompletedReservationEvent(String slackId,
                                        Integer sequence) {
}
