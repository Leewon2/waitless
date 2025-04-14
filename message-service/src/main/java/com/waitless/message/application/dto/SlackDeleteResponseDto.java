package com.waitless.message.application.dto;

import java.util.UUID;

public record SlackDeleteResponseDto(UUID id, String receiverId, String message, boolean isDeleted) {
}
