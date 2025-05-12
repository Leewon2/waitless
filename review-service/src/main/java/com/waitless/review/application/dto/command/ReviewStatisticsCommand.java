package com.waitless.review.application.dto.command;

import com.waitless.common.domain.UserInfoDto;

import java.util.List;
import java.util.UUID;

public record ReviewStatisticsCommand(
        List<UUID> restaurantIds,
        UserInfoDto userInfoDto
) {}