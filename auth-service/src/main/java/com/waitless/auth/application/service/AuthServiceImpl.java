package com.waitless.auth.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final TokenManager tokenManager;
	private final RefreshTokenService refreshTokenService;

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
