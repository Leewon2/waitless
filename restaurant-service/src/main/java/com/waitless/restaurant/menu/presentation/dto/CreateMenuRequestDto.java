package com.waitless.restaurant.menu.presentation.dto;

import java.util.UUID;

public record CreateMenuRequestDto(UUID restaurantId, String category, Integer price, String name, Integer amount) {
}
