package com.waitless.auth.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.waitless.auth.infrastructure.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
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
}
