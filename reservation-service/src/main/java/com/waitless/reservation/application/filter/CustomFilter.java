package com.waitless.reservation.application.filter;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.interceptor.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.waitless.reservation.exception.exception.ReservationErrorCode.RESERVATION_UNAUTHORIZED;

@Slf4j(topic = "CustomFilter")
@Order(1)
@Component
public class CustomFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("doFilter");

        String path = servletRequest.getRequestURI();
        if (path.contains("/v3/api-docs") || path.contains("/swagger-ui") || path.contains("/swagger-resources") || path.contains("/actuator")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String userIdHeader = servletRequest.getHeader("X-User-Id");
        String userRoleHeader = servletRequest.getHeader("X-User-Role");

        try {
            if (userIdHeader == null || userRoleHeader == null || userIdHeader.isEmpty() || userRoleHeader.isEmpty()) {
                throw BusinessException.from(RESERVATION_UNAUTHORIZED);
            }

            UserContext context = UserContext.builder()
                    .userId(Long.parseLong(userIdHeader))
                    .role(userRoleHeader)
                    .build();

            UserContext.setUserContext(context);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "검증 실패");
        } finally {
            log.info("clear ThreadLocal");
            UserContext.clear();
        }

    }
}
