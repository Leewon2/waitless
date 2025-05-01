package com.waitless.benefit.point.domain.vo;

import java.util.UUID;

public record PointSearchCondition(
        UUID reviewId,
        Long userId
) {}
