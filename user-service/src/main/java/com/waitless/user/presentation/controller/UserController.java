package com.waitless.user.presentation.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.dto.UserResponseDto;
import com.waitless.user.application.dto.ValidateUserResponseDto;
import com.waitless.user.application.service.UserService;
import com.waitless.user.presentation.dto.ReadUsersRequestDto;
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
	public ResponseEntity<SingleResponse<UserResponseDto>> readUser(@PathVariable Long id, @RequestHeader("X-User-Role") String roleHeader) {
		Role role = Role.valueOf(roleHeader.toUpperCase());
		return ResponseEntity.ok(SingleResponse.success(userService.findUser(id, role)));
	}

	// 유저 전체 조회
	@RoleCheck(roles = {Role.ADMIN, Role.OWNER})
	@GetMapping
	public ResponseEntity<?> readUsers(
		@RequestParam(required = false) String name,
		@RequestParam(defaultValue = "1", required = false) int page,
		@RequestParam(defaultValue = "10", required = false) int size,
		@RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection,
		@RequestParam(defaultValue = "updatedAt", required = false) String sortBy,
		@RequestHeader("X-User-Role") String role
	) {
		System.out.println(role);
		ReadUsersRequestDto readUsersRequestDto = new ReadUsersRequestDto(name, page, size, sortDirection, sortBy);
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<UserResponseDto> response = userService.findAndSearchUsers(userControllerMapper.toReadUsersDto(readUsersRequestDto), pageable);
		return ResponseEntity.ok(MultiResponse.success(response));
	}

	// 유저 수정
	@PatchMapping("/{id}")
	public ResponseEntity<SingleResponse<UserResponseDto>> updateUser(
		@PathVariable Long id,
		@RequestBody Map<String, Object> updates,
		@RequestHeader("X-User-Role") String roleHeader) {
		Role role = Role.valueOf(roleHeader.toUpperCase());
		UserResponseDto userResponseDto = userService.modifyUser(id, updates, role);
		return ResponseEntity.ok(SingleResponse.success(userResponseDto));
	}

	// 유저 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestHeader("X-User-Role") String roleHeader) {
		Role role = Role.valueOf(roleHeader.toUpperCase());
		userService.removeUser(id, role);
		return ResponseEntity.noContent().build();
	}
}
