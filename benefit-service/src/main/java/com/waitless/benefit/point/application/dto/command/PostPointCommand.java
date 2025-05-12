package com.waitless.benefit.point.application.dto.command;

import com.waitless.benefit.point.domain.vo.PointType;

import java.util.UUID;

public record PostPointCommand(
        Long userId,
        UUID reviewId,
        UUID reservationId,
        Integer amount,
        PointType.Type pointType,
        String description
) {}
