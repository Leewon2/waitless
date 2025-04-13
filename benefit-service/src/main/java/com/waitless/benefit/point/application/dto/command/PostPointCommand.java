package com.waitless.benefit.point.application.dto.command;

import com.waitless.benefit.point.domain.vo.PointType;

public record PostPointCommand(
        Long userId,
        Integer amount,
        PointType.Type pointType,
        String description
) {}
