package com.waitless.auth.application.service;

import java.util.Optional;

import com.waitless.auth.application.dto.LoginResponseDto;

public interface AuthService {

	LoginResponseDto login(String email, String password);

	Optional<String> generateNewAccessTokenByRefreshToken(String refreshToken);

	void logout(Long userId);
}
