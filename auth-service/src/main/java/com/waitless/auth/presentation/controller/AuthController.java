package com.waitless.auth.presentation.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.auth.application.dto.LoginResponseDto;
import com.waitless.auth.application.exception.AuthErrorCode;
import com.waitless.auth.application.service.AuthService;
import com.waitless.auth.presentation.dto.LoginRequestDto;
import com.waitless.auth.presentation.dto.RefreshTokenRequestDto;
import com.waitless.common.exception.response.SingleResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
		LoginResponseDto loginResponseDto = authService.login(loginRequestDto.email(), loginRequestDto.password());

		response.setHeader("Authorization", loginResponseDto.accessToken());
		response.setHeader("Refresh-Token", loginResponseDto.refreshToken());
		response.setContentType("application/json");

		return ResponseEntity.ok(loginResponseDto);
	}

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

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<SingleResponse<String>> logout(@RequestHeader("X-User-Id")String userId) {
		authService.logout(Long.parseLong(userId));
		return ResponseEntity.ok(SingleResponse.success("로그아웃 완료"));
	}
}
