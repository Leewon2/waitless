package com.waitless.auth.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.auth.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final JwtUtil jwtUtil;
	private final RefreshTokenService refreshTokenService;

	// 리프레시 토큰으로 새로운 액세스 토큰 생성
	@Override
	public Optional<String> generateNewAccessTokenByRefreshToken(String refreshToken) {
		if (!jwtUtil.validateToken(refreshToken)) {
			return Optional.empty();
		}
		String userId = jwtUtil.getUserIdFromToken(refreshToken);
		String role = jwtUtil.getUserRoleFromToken(refreshToken);
		Optional<String> storedRefreshToken = refreshTokenService.findRefreshTokenByUserId(userId);
		if (storedRefreshToken.isPresent() && storedRefreshToken.get().equals(refreshToken)) {
			return Optional.of(jwtUtil.generateAccessToken(userId, role));
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
