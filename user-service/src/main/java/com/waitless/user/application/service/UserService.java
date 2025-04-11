package com.waitless.user.application.service;

import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.application.dto.ValidateUserDto;
import com.waitless.user.application.dto.ValidateUserResponseDto;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.presentation.dto.ValidateUserRequestDto;

public interface UserService {
	SignupResponseDto signup(SignupDto signupDto);

	ValidateUserResponseDto validateUser(ValidateUserDto validateUserDto);
}
