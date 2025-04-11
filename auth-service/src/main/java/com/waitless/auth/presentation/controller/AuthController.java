package com.waitless.auth.presentation.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.auth.application.exception.AuthErrorCode;
import com.waitless.auth.application.service.AuthService;
import com.waitless.auth.presentation.dto.RefreshTokenRequestDto;
import com.waitless.common.exception.response.SingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	// 저장된 Refresh Token으로 새로운 Access Token 발급
	@PostMapping("/refresh")
	public ResponseEntity<SingleResponse<String>> generateNewAccessTokenByRefreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
		Optional<String> newAccessToken = authService.generateNewAccessTokenByRefreshToken(refreshTokenRequestDto.refreshToken());
		return newAccessToken
			.map(token -> ResponseEntity.ok(SingleResponse.success(token)))
			.orElseGet(() -> ResponseEntity
				.badRequest()
				.body(SingleResponse.error(
					AuthErrorCode.AUTH_TOKEN_INVALID.getCode(),
					AuthErrorCode.AUTH_TOKEN_INVALID.getMessage()
				))
			);
	}
}
