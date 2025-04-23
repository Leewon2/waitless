package com.waitless.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ADMIN, USER, OWNER,
	@SuppressWarnings("unused") ALL;	// 어노테이션에서만 사용, 요청 헤더에는 안 들어옴.
}
