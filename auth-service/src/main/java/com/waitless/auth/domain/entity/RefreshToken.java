package com.waitless.auth.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 604800) // TTL 7일
public class RefreshToken {
	@Id
	private String id;
	private String refreshToken;
	private LocalDateTime expiryDate;
}
