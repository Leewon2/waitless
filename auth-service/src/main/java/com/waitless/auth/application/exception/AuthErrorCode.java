package com.waitless.auth.application.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.waitless.common.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {

	AUTH_LOGIN_FAILED("AUTH_001", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
	AUTH_TOKEN_EXPIRED("AUTH_002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	AUTH_TOKEN_INVALID("AUTH_003", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
	AUTH_PARSING_FAILED("AUTH_004", "로그인 요청 파싱에 실패했습니다.", HttpStatus.BAD_REQUEST);

	private final String code;
	private final String message;
	private final int status;

	AuthErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
