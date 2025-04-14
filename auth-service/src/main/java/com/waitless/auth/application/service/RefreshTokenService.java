package com.waitless.auth.application.service;

import java.util.Optional;

public interface RefreshTokenService {
	void saveOrUpdateToken(String userId, String refreshToken);

	Optional<String> findRefreshTokenByUserId(String userId);

	void deleteRefreshToken(Long userId);
}
