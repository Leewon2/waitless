package com.waitless.restaurant.restaurant.presentation.dto;

import java.util.UUID;

public record ReviewStatisticsBatchResponseDto(
    UUID restaurantId,
    long reviewCount,
    double averageRating
){}