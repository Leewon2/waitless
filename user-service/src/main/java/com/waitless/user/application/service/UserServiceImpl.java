package com.waitless.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	@Override
	public UserResponseDto findUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> UserBusinessException.from(UserErrorCode.USER_NOT_FOUND));
		return userServiceMapper.toUserResponseDto(user);
	}

}
