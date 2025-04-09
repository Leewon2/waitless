package com.waitless.user.application.dto;

import com.waitless.user.domain.entity.Role;

public record SignupDto(
	String email, String password, String name, String phone, Role role, String slackId) {
}
