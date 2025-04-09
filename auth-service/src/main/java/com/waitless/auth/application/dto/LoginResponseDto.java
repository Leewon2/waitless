package com.waitless.auth.application.dto;

public record LoginResponseDto(String accessToken, String refreshToken, String message) {
}
