package com.waitless.user.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.waitless.user.application.dto.ReadUsersDto;
import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.dto.ValidateUserDto;
import com.waitless.user.presentation.dto.ReadUsersRequestDto;
import com.waitless.user.presentation.dto.SignupRequestDto;
import com.waitless.user.presentation.dto.ValidateUserRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserControllerMapper {
	SignupDto toSignupDto(SignupRequestDto signupRequestDto);

	ValidateUserDto toValidateUserDto(ValidateUserRequestDto validateUserRequestDto);

	ReadUsersDto toReadUsersDto(ReadUsersRequestDto readUsersRequestDto);
}
