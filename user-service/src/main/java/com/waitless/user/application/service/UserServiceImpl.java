package com.waitless.user.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.common.domain.Role;
import com.waitless.user.application.dto.ReadUsersDto;
import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.dto.UserResponseDto;
import com.waitless.user.application.dto.ValidateUserDto;
import com.waitless.user.application.dto.ValidateUserResponseDto;
import com.waitless.user.application.exception.UserBusinessException;
import com.waitless.user.application.exception.UserErrorCode;
import com.waitless.user.application.mapper.UserServiceMapper;
import com.waitless.user.domain.entity.User;
import com.waitless.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserServiceMapper userServiceMapper;

	// 회원가입
	@Override
	@Transactional
	public SignupResponseDto signup(SignupDto signupDto) {
		String encodedPassword = passwordEncoder.encode(signupDto.password());
		User user = userServiceMapper.toUser(signupDto, encodedPassword);
		return userServiceMapper.toSignupResponseDto(userRepository.save(user));
	}

	// 유저 검증
	@Override
	public ValidateUserResponseDto validateUser(ValidateUserDto validateUserDto) {
		User user = userRepository.findByEmail(validateUserDto.email())
			.orElseThrow(() -> UserBusinessException.from(UserErrorCode.USER_NOT_FOUND));
		if (!passwordEncoder.matches(validateUserDto.password(), user.getPassword())) {
			throw UserBusinessException.from(UserErrorCode.USER_INVALID_PASSWORD);
		}
		return userServiceMapper.toValidateUserResponseDto(user);
	}

	// 유저 단건 조회
	@Override
	public UserResponseDto findUser(Long id, Role role) {
		User user = findUserById(id);
		if (role == Role.USER) {	// 로그인유저가 USER일 경우, 본인 정보만 확인 가능
			if (id != user.getId()) {
				throw UserBusinessException.from(UserErrorCode.USER_UNAUTHORIZED);
			}
		}
		return userServiceMapper.toUserResponseDto(user);
	}

	// 유저 전체 조회 + 검색
	@Override
	public Page<UserResponseDto> findAndSearchUsers(ReadUsersDto readUsersDto, Pageable pageable) {
		Page<User> userList = userRepository.findAndSearchUsers(readUsersDto.name(), readUsersDto.sortDirection(), readUsersDto.sortBy(), pageable);
		List<UserResponseDto> dtoList = userList
			.stream()
			.map(userServiceMapper::toUserResponseDto)
			.toList();
		return new PageImpl<>(dtoList, pageable, userList.getTotalElements());
	}

	// 유저 수정
	@Override
	@Transactional
	public UserResponseDto modifyUser(Long id, Map<String, Object> updates, Role role) {
		User user = findUserById(id);
		if (!validateUser(id, user.getId(), role)) {
			throw UserBusinessException.from(UserErrorCode.USER_UNAUTHORIZED);
		}
		updates.forEach((key, value) -> user.modifyUserInfo(key, value));
		return userServiceMapper.toUserResponseDto(user);
	}

	// 유저 삭제
	@Override
	@Transactional
	public void removeUser(Long id, Role role) {
		User user = findUserById(id);
		if (!validateUser(id, user.getId(), role)) {
			throw UserBusinessException.from(UserErrorCode.USER_UNAUTHORIZED);
		}
		user.delete();
	}

	private User findUserById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(()-> UserBusinessException.from(UserErrorCode.USER_NOT_FOUND));
		return user;
	}

	private Boolean validateUser(Long id, Long userId, Role role) {
		if (role != Role.ADMIN) {	// 로그인유저가 ADMIN이 아닐 경우, 본인 정보만 수정/삭제 가능
			if (id != userId) {
				return false;
			}
		}
		return true;
	}

}
