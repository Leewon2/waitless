package com.waitless.benefit.point.application.dto.result;

import lombok.Builder;

@Builder
public record GetPointAmountResult(
        Long userId,
        int totalPoint
) {
    public static GetPointAmountResult of(Long userId, int totalPoint) {
        return new GetPointAmountResult(userId, totalPoint);
    }
}
