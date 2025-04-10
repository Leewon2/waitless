package com.waitless.gateway.security;

import java.security.Key;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final Key key;

	public JwtUtil(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// userId 파싱
	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// role 파싱
	public String getUserRoleFromToken(String token) {
		return getClaimFromToken(token, claims -> claims.get("role", String.class));
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claimsResolver.apply(claims);
	}
}