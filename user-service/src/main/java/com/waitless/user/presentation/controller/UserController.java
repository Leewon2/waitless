package com.waitless.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.service.UserService;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.presentation.mapper.UserControllerMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserControllerMapper userControllerMapper;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<SingleResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
		return ResponseEntity.ok(SingleResponse.success(userService.signup(userControllerMapper.toServiceDto(signupRequestDto))));
	}
}
