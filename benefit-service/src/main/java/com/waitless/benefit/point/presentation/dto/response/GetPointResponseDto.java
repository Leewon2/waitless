package com.waitless.benefit.point.presentation.dto.response;

import com.waitless.benefit.point.application.dto.result.GetPointResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetPointResponseDto(
        UUID pointId,
        Long userId,
        UUID reviewId,
        UUID reservationId,
        int amount,
        String type,
        String description,
        LocalDateTime createdAt
) {
    public static GetPointResponseDto from(GetPointResult result) {
        return GetPointResponseDto.builder()
                .pointId(result.pointId())
                .userId(result.userId())
                .reviewId(result.reviewId())
                .reservationId(result.reservationId())
                .amount(result.amount().getPointValue())
                .type(result.type().getPointType().name())
                .description(result.description())
                .createdAt(result.createdAt())
                .build();
    }
}