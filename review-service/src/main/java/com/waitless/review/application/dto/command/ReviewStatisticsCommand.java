package com.waitless.review.application.dto.command;

import java.util.List;
import java.util.UUID;

public record ReviewStatisticsCommand(
        List<UUID> restaurantIds
) {}