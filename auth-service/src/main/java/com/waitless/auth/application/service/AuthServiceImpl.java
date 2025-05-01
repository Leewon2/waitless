package com.waitless.auth.application.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.auth.application.client.UserServiceClient;
import com.waitless.auth.application.dto.LoginResponseDto;
import com.waitless.auth.application.dto.ValidateUserRequestDto;
import com.waitless.auth.application.dto.ValidateUserResponseDto;
import com.waitless.auth.application.exception.AuthBusinessException;
import com.waitless.auth.application.exception.AuthErrorCode;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserServiceClient userServiceClient;
	private final TokenManager tokenManager;
	private final RefreshTokenService refreshTokenService;

	@Override
	public LoginResponseDto login(String email, String password) {
		try {
			ValidateUserResponseDto validated = userServiceClient.validateUser(
				new ValidateUserRequestDto(email, password)
			);
			if (validated == null) {
				throw AuthBusinessException.from(AuthErrorCode.AUTH_LOGIN_FAILED);
			}

			String userId = String.valueOf(validated.userId());
			String role = validated.role();

			String accessToken = tokenManager.generateAccessToken(userId, role);
			String refreshToken = tokenManager.generateRefreshToken(userId);

			refreshTokenService.saveOrUpdateToken(userId, refreshToken);

			return new LoginResponseDto(accessToken, refreshToken, "로그인 성공");
		} catch (FeignException.Unauthorized e) {
			throw AuthBusinessException.from(AuthErrorCode.AUTH_LOGIN_FAILED);
		}
	}

	// 리프레시 토큰으로 새로운 액세스 토큰 생성
	@Override
	public Optional<String> generateNewAccessTokenByRefreshToken(String refreshToken) {
		if (!tokenManager.validateToken(refreshToken)) {
			return Optional.empty();
		}
		String userId = tokenManager.getUserIdFromToken(refreshToken);
		String role = tokenManager.getUserRoleFromToken(refreshToken);
		Optional<String> storedRefreshToken = refreshTokenService.findRefreshTokenByUserId(userId);
		if (storedRefreshToken.isPresent() && storedRefreshToken.get().equals(refreshToken)) {
			return Optional.of(tokenManager.generateAccessToken(userId, role));
		}
		return Optional.empty();
	}

	@Override
	public void logout(Long userId) {
		log.info("userId : {}", userId);
		refreshTokenService.deleteRefreshToken(userId);
		log.info("{}의 Refresh Token 삭제 완료", userId);
	}
}
