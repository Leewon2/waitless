// package com.waitless.auth.infrastructure.security;
//
// import java.io.IOException;
//
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.waitless.auth.application.client.UserServiceClient;
// import com.waitless.auth.application.dto.LoginResponseDto;
// import com.waitless.auth.application.dto.ValidateUserRequestDto;
// import com.waitless.auth.application.dto.ValidateUserResponseDto;
// import com.waitless.auth.application.exception.AuthBusinessException;
// import com.waitless.auth.application.exception.AuthErrorCode;
// import com.waitless.auth.application.service.RefreshTokenService;
// import com.waitless.auth.presentation.dto.LoginRequestDto;
//
// import feign.FeignException;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Component
// @RequiredArgsConstructor
// @Slf4j
// public class AuthenticationFilter extends OncePerRequestFilter {
//
// 	private final ObjectMapper objectMapper = new ObjectMapper();
// 	private final UserServiceClient userServiceClient;
// 	private final JwtTokenManager jwtTokenManager;
// 	private final RefreshTokenService refreshTokenService;
//
// 	@Override
// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
// 		FilterChain filterChain) throws ServletException, IOException {
// 		if (!request.getRequestURI().equals("/api/auth/login")) {
// 			log.info("i'm not login: " + request.getRequestURI());
// 			filterChain.doFilter(request, response);
// 			return;
// 		}
// 		try {
// 			log.info("login: " + request.getRequestURI());
// 			// 로그인 요청 데이터
// 			LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
// 			log.info("email : {}",loginRequestDto.email());
// 			log.info("password : {}",loginRequestDto.password());
// 			// 유저 검증
// 			ValidateUserResponseDto validateUserResponseDto = userServiceClient.validateUser(
// 				new ValidateUserRequestDto(loginRequestDto.email(), loginRequestDto.password())
// 			);
// 			log.info("userId = {}", validateUserResponseDto.userId());
// 			if (validateUserResponseDto == null) {
// 				throw AuthBusinessException.from(AuthErrorCode.AUTH_LOGIN_FAILED);
// 			}
//
// 			String userId = String.valueOf(validateUserResponseDto.userId());	// 유저 id(pk)
// 			String role = validateUserResponseDto.role();	// 유저 역할
//
// 			// JWT Token 생성
// 			String accessToken = jwtTokenManager.generateAccessToken(userId, role);
// 			String refreshToken = jwtTokenManager.generateRefreshToken(userId);
//
// 			// Refresh Token Redis에 저장
// 			refreshTokenService.saveOrUpdateToken(userId, refreshToken);
//
// 			// Header에 Token 응답
// 			response.setHeader("Authorization", accessToken);
// 			response.setHeader("Refresh-Token", refreshToken);
// 			response.setContentType("application/json");
//
// 			LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken, "로그인 성공");
// 			objectMapper.writeValue(response.getOutputStream(), loginResponseDto);
// 		} catch (FeignException.Unauthorized e) {
// 			throw AuthBusinessException.from(AuthErrorCode.AUTH_LOGIN_FAILED);
// 		} catch (IOException e) {
// 			throw AuthBusinessException.from(AuthErrorCode.AUTH_PARSING_FAILED);
// 		}
// 	}
// }
