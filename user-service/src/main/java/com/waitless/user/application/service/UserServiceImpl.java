package com.waitless.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.application.mapper.UserServiceMapper;
import com.waitless.user.domain.entity.User;
import com.waitless.user.domain.repository.UserRepository;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.application.dto.SignupResponseDto;

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
		return userServiceMapper.toResponseDto(userRepository.save(user));
	}
}
