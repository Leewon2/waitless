package com.waitless.user.application.dto;

import org.springframework.data.domain.Sort;

public record ReadUsersDto(String name, int page, int size, Sort.Direction sortDirection, String sortBy) {
}
