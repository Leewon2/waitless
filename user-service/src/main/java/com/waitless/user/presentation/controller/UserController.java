package com.waitless.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.dto.ValidateUserResponseDto;
import com.waitless.user.application.service.UserService;
import com.waitless.user.application.dto.UserResponseDto;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.presentation.dto.ValidateUserRequestDto;
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
		return ResponseEntity.ok(SingleResponse.success(userService.signup(userControllerMapper.toSignupDto(signupRequestDto))));
	}

	// 유저 검증
	@PostMapping("/app/validate")
	public ValidateUserResponseDto validateUser(@RequestBody ValidateUserRequestDto validateUserRequestDto) {
		return userService.validateUser(userControllerMapper.toValidateUserDto(validateUserRequestDto));
	}

	// 유저 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<SingleResponse<UserResponseDto>> readUser(@PathVariable Long id) {
		return ResponseEntity.ok(SingleResponse.success(userService.findUser(id)));
	}
}
