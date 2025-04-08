package com.waitless.restaurant.menu.presentation.dto;

import java.util.UUID;

public record CreateMenuRequestDto(UUID restaurantId, String category, int price, String name, int amount) {
}
