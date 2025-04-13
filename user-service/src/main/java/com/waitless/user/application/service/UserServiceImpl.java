package com.waitless.user.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.waitless.user.presentation.dto.ReadUsersRequestDto;

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
	public UserResponseDto findUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> UserBusinessException.from(UserErrorCode.USER_NOT_FOUND));
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

}
