package com.waitless.reservation.infrastructure.adaptor.client;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.infrastructure.adaptor.client.dto.UserResponseDto;
import com.waitless.reservation.infrastructure.config.feign.FeignCommonConfig;
import com.waitless.reservation.infrastructure.config.feign.UserFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "${gateway.url}",
        configuration = {FeignCommonConfig.class, UserFeignConfig.class}
)
public interface UserClient {
    @GetMapping("/user-service/api/users/{userId}")
    ResponseEntity<SingleResponse<UserResponseDto>> readUser(@PathVariable Long userId);
}
