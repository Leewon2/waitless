package com.waitless.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GlobalFilter, Ordered {

	private final JwtUtil jwtUtil;

	@PostConstruct
	public void init() {
		log.info("Bean 초기화");
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String path = request.getURI().getPath();
		log.info("Request path: {}", path);


		if (path.startsWith("/v3/api-docs") ||
				path.startsWith("/swagger-ui") ||
				path.startsWith("/swagger-ui.html") ||
				path.startsWith("/user-service/v3/api-docs") ||
				path.startsWith("/auth-service/v3/api-docs") ||
				path.startsWith("/restaurant-service/v3/api-docs") ||
				path.startsWith("/reservation-service/v3/api-docs") ||
				path.startsWith("/review-service/v3/api-docs") ||
				path.startsWith("/benefit-service/v3/api-docs") ||
				path.startsWith("/message-service/v3/api-docs")) {
			log.debug("Swagger 요청 → 인증 필터 통과");
			return chain.filter(exchange);
		}

		if (path.startsWith("/api/users/signup") || path.startsWith("/api/auth/signin") || path.startsWith("/api/auth/validate")

			|| path.startsWith("/api/auth/refresh")) {
			log.debug("인증 예외 경로 → 필터 통과");
			return chain.filter(exchange);
		}
		 String token = request.getHeaders().getFirst("Authorization");
		 if (token == null || !token.startsWith("Bearer ")) {
		 	log.warn("JWT Token 누락 또는 형식 오류 → 401 UnAuthorized");
		 	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		 	return exchange.getResponse().setComplete();
		 }

		 token = token.substring(7);
		 if (!jwtUtil.validateToken(token)) {
		 	log.warn("유효하지 않은 JWT Token → 요청 차단");
		 	return exchange.getResponse().setComplete();
		 }

		 // userId는 Long이지만 헤더에는 String으로 전달됨.
		 String userId = jwtUtil.getUserIdFromToken(token);
		 String role = jwtUtil.getUserRoleFromToken(token);
		 log.info("유저 인증 완료: userId={}, role={}", userId, role);

		ServerHttpRequest modifiedRequest = request.mutate()
			.header("X-User-Id", userId)
			.header("X-User-Role", role)
			.build();
		return chain.filter(exchange.mutate().request(modifiedRequest).build());
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
