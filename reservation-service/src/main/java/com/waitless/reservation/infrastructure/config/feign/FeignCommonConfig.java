package com.waitless.reservation.infrastructure.config.feign;

import com.waitless.reservation.application.interceptor.CustomHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCommonConfig {
    @Bean
    public CustomHeaderInterceptor customHeaderInterceptor() {
        return new CustomHeaderInterceptor();
    }
}