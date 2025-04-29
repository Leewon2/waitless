package com.waitless.benefit.point.application.dto.result;

import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetPointResult(
        UUID pointId,
        Long userId,
        UUID reviewId,
        UUID reservationId,
        PointAmount amount,
        PointType type,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static GetPointResult from(Point point) {
        return GetPointResult.builder()
                .pointId(point.getId())
                .userId(point.getUserId())
                .reviewId(point.getReviewId())
                .reservationId(point.getReservationId())
                .amount(point.getAmount())
                .type(point.getType())
                .description(point.getDescription())
                .createdAt(point.getCreatedAt())
                .updatedAt(point.getUpdatedAt())
                .build();
    }
}
