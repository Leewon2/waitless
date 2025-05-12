package com.waitless.auth.infrastructure.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.waitless.auth.domain.repository.RefreshTokenRepository;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

	private final RedisTemplate<String, Object> redisTemplate;
	private final long refreshTokenExpiration;

	public RefreshTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate,
		@Value("${jwt.refresh-expiration}") long refreshTokenExpiration) {
		this.redisTemplate = redisTemplate;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	// Refresh Token → Redis에 저장
	@Override
	public void saveToken(String userId, String refreshToken) {
		redisTemplate.opsForValue().set("RT:" + userId, refreshToken, refreshTokenExpiration, TimeUnit.DAYS);
	}

	// userId로 저장된 Refresh Token 찾기
	@Override
	public Optional<String> findRefreshTokenByUserId(String userId) {
		String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + userId);
		return Optional.ofNullable(refreshToken);
	}

	// 저장된 Refresh Token 삭제
	@Override
	public void deleteRefreshToken(Long userId) {
		redisTemplate.delete("RT:" + userId);
	}
}
