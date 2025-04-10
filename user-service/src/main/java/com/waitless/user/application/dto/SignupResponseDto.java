package com.waitless.user.application.dto;

import com.waitless.user.domain.entity.Role;

public record SignupResponseDto(
	Long id, String name, String phone, Role role, String slackId) {
}
