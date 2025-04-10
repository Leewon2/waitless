package com.waitless.restaurant.menu.presentation.dto;

import org.springframework.lang.Nullable;

import java.util.UUID;

public record UpdateMenuRequestDto(@Nullable String category,
                                   @Nullable Integer price,
                                   @Nullable String name,
                                   @Nullable Integer amount) {
}
