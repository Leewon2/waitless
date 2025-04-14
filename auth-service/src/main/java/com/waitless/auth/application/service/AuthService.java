package com.waitless.auth.application.service;

import java.util.Optional;

public interface AuthService {
	Optional<String> generateNewAccessTokenByRefreshToken(String refreshToken);

	void logout(Long userId);
}
