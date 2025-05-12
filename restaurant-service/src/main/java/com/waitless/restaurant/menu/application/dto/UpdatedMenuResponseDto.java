package com.waitless.restaurant.menu.application.dto;

import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

import java.util.UUID;

public record UpdatedMenuResponseDto(UUID menuId, UUID restaurantId,
                                     MenuCategory menuCategory, int price,
                                     String name, int amount) {
}
