package com.waitless.auth.domain.repository;

import java.util.Optional;

public interface RefreshTokenRepository {
	void saveToken(String userId, String refreshToken);

	Optional<String> findRefreshTokenByUserId(String userId);

	void deleteRefreshToken(Long userId);
}
