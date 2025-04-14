package com.waitless.user.application.dto;

import com.waitless.user.domain.entity.Role;

public record UserResponseDto(
	Long id, String name, String phone, Role role, String slackId) {
}
