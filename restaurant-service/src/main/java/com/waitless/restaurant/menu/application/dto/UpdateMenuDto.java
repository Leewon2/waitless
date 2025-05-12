package com.waitless.restaurant.menu.application.dto;

import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

import java.util.UUID;

public record UpdateMenuDto(MenuCategory category, Integer price, String name, Integer amount) {
}
