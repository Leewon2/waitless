package com.waitless.restaurant.menu.application.dto;

import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

import java.util.UUID;

public record SearchResponseDto(UUID restaurantId, MenuCategory category, int price, String name, int amount) {
}
