package com.waitless.auth.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.auth.domain.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

	private final RefreshTokenRepository refreshTokenRepository;

	// Refresh Token → Redis에 저장
	@Override
	@Transactional
	public void saveOrUpdateToken(String userId, String refreshToken) {
		refreshTokenRepository.saveToken(userId, refreshToken);
	}

	// userId로 저장된 Refresh Token 찾기
	@Override
	public Optional<String> findRefreshTokenByUserId(String userId) {
		return refreshTokenRepository.findRefreshTokenByUserId(userId);
	}

	// Refresh Token 삭제
	@Override
	public void deleteRefreshToken(Long userId) {
		refreshTokenRepository.deleteRefreshToken(userId);
	}
}
