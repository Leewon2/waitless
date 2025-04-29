package com.waitless.benefit.point.application.dto.result;

import lombok.Builder;

@Builder
public record GetMyRankingResult(
        Long userId,
        int totalPoint,
        int rank
) {
    public static GetMyRankingResult of(Long userId, int totalPoint, int rank) {
        return new GetMyRankingResult(userId, totalPoint, rank);
    }
}
