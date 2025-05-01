package com.waitless.benefit.point.application.dto.command;

import com.waitless.common.domain.UserInfoDto;

public record SearchRankingCommand(
        int topN,
        UserInfoDto userInfoDto
) {}