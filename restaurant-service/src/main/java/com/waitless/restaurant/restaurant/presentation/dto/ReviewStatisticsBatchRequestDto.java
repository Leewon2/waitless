package com.waitless.restaurant.restaurant.presentation.dto;

import java.util.List;
import java.util.UUID;

public record ReviewStatisticsBatchRequestDto(
        List<UUID> restaurantIds
) {}