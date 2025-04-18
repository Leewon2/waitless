package com.waitless.benefit.point.application.dto.result;

import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostPointResult(
        UUID pointId,
        Long userId,
        UUID reviewId,
        UUID reservationId,
        PointAmount amount,
        PointType type,
        String description,
        LocalDateTime createdAt
) {
    public static PostPointResult from(Point point) {
        return new PostPointResult(
                point.getId(),
                point.getUserId(),
                point.getReviewId(),
                point.getReservationId(),
                point.getAmount(),
                point.getType(),
                point.getDescription(),
                point.getCreatedAt()
        );
    }
}
