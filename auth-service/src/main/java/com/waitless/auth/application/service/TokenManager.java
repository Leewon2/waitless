package com.waitless.auth.application.service;

public interface TokenManager {

	String generateAccessToken(String userId, String role);

	String generateRefreshToken(String userId);

	boolean validateToken(String token);

	String getUserIdFromToken(String token);

	String getUserRoleFromToken(String token);
}
