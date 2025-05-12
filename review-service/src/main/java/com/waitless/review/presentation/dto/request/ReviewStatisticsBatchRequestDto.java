package com.waitless.review.presentation.dto.request;

import java.util.List;
import java.util.UUID;

public record ReviewStatisticsBatchRequestDto(
        List<UUID> restaurantIds
) {}