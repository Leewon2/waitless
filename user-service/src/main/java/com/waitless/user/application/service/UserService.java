package com.waitless.user.application.service;

import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.application.dto.SignupResponseDto;

public interface UserService {
	public SignupResponseDto signup(SignupDto signupDto);
}
