package com.waitless.restaurant.restaurant.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavoriteResponseDto(UUID id, UUID restaurantId, String restaurantName, LocalDateTime createdAt) {

}
