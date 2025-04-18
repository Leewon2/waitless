package com.waitless.reservation.infrastructure.config.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFeignConfig {
    @Bean(name = "userErrorDecoder")
    public ErrorDecoder errorDecoder() {
        return new UserClientErrorDecoder();
    }
}