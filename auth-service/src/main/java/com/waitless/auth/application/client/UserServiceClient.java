package com.waitless.auth.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waitless.auth.application.dto.ValidateUserRequestDto;
import com.waitless.auth.application.dto.ValidateUserResponseDto;

@FeignClient(name = "user-service", path = "/api/users/app")
public interface UserServiceClient {

	// 유저 존재 여부
	@PostMapping("/validate")
	ValidateUserResponseDto validateUser(@RequestBody ValidateUserRequestDto requestDto);
}
