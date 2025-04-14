package com.waitless.restaurant.menu.application.dto;

import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

public record SearchMenuDto(Integer minPrice, Integer maxPrice, MenuCategory category, String sortBy, Integer page, Integer size) {
}
