package com.waitless.benefit.coupon.application.dto;

import org.springframework.data.domain.Sort;

public record ReadCouponsDto(String title, int page, int size, Sort.Direction sortDirection, String sortBy) {
}
