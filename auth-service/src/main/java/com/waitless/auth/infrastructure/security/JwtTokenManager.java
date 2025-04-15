package com.waitless.auth.infrastructure.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.waitless.auth.application.exception.AuthBusinessException;
import com.waitless.auth.application.exception.AuthErrorCode;
import com.waitless.auth.application.service.TokenManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenManager implements TokenManager {

	private static final String BEARER_PREFIX = "Bearer ";
	private final Key key;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;

	public JwtTokenManager(@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.access-expiration}") long accessTokenExpiration,
		@Value("${jwt.refresh-expiration}") long refreshTokenExpiration) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	// Access Token 생성
	public String generateAccessToken(String userId, String role) {
		String token = generateToken(userId, role, accessTokenExpiration);
		return BEARER_PREFIX + token;
	}

	// Refresh Token 생성
	public String generateRefreshToken(String userId) {
		String token = generateToken(userId, "REFRESH", refreshTokenExpiration);
		return BEARER_PREFIX + token;
	}

	// JWT Token 생성
	private String generateToken(String userId, String role, long expiration) {
		return Jwts.builder()
			.subject(userId)
			.claim("role", role)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(removeBearerPrefix(token));

			return true;
		} catch (ExpiredJwtException e) {
			throw AuthBusinessException.from(AuthErrorCode.AUTH_TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw AuthBusinessException.from(AuthErrorCode.AUTH_TOKEN_INVALID);
		}
	}

	// userId 파싱
	public String getUserIdFromToken(String token) {
		return getClaimFromToken(removeBearerPrefix(token), Claims::getSubject);
	}

	// role 파싱
	public String getUserRoleFromToken(String token) {
		return getClaimFromToken(removeBearerPrefix(token), claims -> claims.get("role", String.class));
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(removeBearerPrefix(token))
			.getBody();
		return claimsResolver.apply(claims);
	}

	// Bearer 삭제
	private String removeBearerPrefix(String token) {
		if (token != null && token.startsWith(BEARER_PREFIX)) {
			return token.substring(BEARER_PREFIX.length());
		}
		return token;
	}
}
