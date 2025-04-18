package com.waitless.reservation.infrastructure.adaptor.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ADMIN, USER, OWNER;
}
