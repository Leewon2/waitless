package com.waitless.benefit.coupon.presentation.dto;

import org.springframework.data.domain.Sort;

public record ReadCouponsRequestDto(String title, int page, int size, Sort.Direction sortDirection, String sortBy) {
}
