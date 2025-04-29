package com.waitless.common.dto;

public record ReservationCancelEvent(String slackId, String message) {
}
