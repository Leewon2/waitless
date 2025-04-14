package com.waitless.user.presentation.dto;

import org.springframework.data.domain.Sort;

public record ReadUsersRequestDto(String name, int page, int size, Sort.Direction sortDirection, String sortBy) {
}
