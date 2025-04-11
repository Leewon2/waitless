package com.waitless.auth.domain.service;

import java.util.Optional;

public interface RefreshTokenService {
	void saveOrUpdateToken(String userId, String refreshToken);

	Optional<String> findRefreshTokenByUserId(String userId);
}
