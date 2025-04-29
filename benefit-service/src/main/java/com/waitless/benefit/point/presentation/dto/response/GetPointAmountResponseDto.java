package com.waitless.benefit.point.presentation.dto.response;

import com.waitless.benefit.point.application.dto.result.GetPointAmountResult;
import lombok.Builder;

@Builder
public record GetPointAmountResponseDto(
        Long userId,
        int totalPoint
) {
    public static GetPointAmountResponseDto from(GetPointAmountResult result) {
        return GetPointAmountResponseDto.builder()
                .userId(result.userId())
                .totalPoint(result.totalPoint())
                .build();
    }
}