package com.waitless.reservation.infrastructure.adaptor.client.dto;

public record UserResponseDto(
	Long id, String name, String phone, Role role, String slackId) {
}
