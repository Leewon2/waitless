package com.waitless.reservation.infrastructure.config.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantFeignConfig {
    @Bean(name = "restaurantErrorDecoder")
    public ErrorDecoder errorDecoder() {
        return new RestaurantClientErrorDecoder();
    }
}