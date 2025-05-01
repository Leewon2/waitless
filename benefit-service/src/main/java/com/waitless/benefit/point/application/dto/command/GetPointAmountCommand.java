package com.waitless.benefit.point.application.dto.command;

import com.waitless.common.domain.UserInfoDto;

public record GetPointAmountCommand(
        Long userId,
        UserInfoDto userInfoDto
) {}
