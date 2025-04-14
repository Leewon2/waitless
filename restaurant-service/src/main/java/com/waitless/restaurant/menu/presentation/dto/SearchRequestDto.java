package com.waitless.restaurant.menu.presentation.dto;

public record SearchRequestDto(Integer minPrice, Integer maxPrice, String category, String sortBy, Integer page, Integer size) {
}
