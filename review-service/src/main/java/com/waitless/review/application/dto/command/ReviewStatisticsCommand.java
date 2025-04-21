package com.waitless.review.application.dto.command;

import java.util.UUID;

public record ReviewStatisticsCommand(
        UUID restaurantId
) {}