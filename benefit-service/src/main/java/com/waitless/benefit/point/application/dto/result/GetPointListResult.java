package com.waitless.benefit.point.application.dto.result;

import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import com.waitless.benefit.point.application.dto.command.PageCommand;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetPointListResult(
        UUID pointId,
        UUID reviewId,
        UUID reservationId,
        PointAmount amount,
        PointType type,
        String description,
        LocalDateTime createdAt
) {
    public static GetPointListResult from(Point point) {
        return GetPointListResult.builder()
                .pointId(point.getId())
                .reviewId(point.getReviewId())
                .reservationId(point.getReservationId())
                .amount(point.getAmount())
                .type(point.getType())
                .description(point.getDescription())
                .createdAt(point.getCreatedAt())
                .build();
    }
    public static PageCommand<GetPointListResult> toPageCommand(Page<Point> page) {
        return PageCommand.from(page.map(GetPointListResult::from));
    }
}
