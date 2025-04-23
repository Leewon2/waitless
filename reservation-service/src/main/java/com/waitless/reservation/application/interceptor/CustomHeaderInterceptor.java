package com.waitless.reservation.application.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        UserContext user = UserContext.getUserContext();

        if (user != null) {
            template.header("X-User-Id", String.valueOf(user.getUserId()));
            template.header("X-User-Role", user.getRole());
        }

    }
}
