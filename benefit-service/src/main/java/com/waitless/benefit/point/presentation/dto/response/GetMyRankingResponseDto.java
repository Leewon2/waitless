package com.waitless.benefit.point.presentation.dto.response;

import com.waitless.benefit.point.application.dto.result.GetMyRankingResult;
import lombok.Builder;

@Builder
public record GetMyRankingResponseDto(
        Long userId,
        int totalPoint,
        int rank
) {
    public static GetMyRankingResponseDto from(GetMyRankingResult result) {
        return GetMyRankingResponseDto.builder()
                .userId(result.userId())
                .totalPoint(result.totalPoint())
                .rank(result.rank())
                .build();
    }
}