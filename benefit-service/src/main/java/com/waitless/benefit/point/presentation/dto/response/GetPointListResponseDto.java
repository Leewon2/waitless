package com.waitless.benefit.point.presentation.dto.response;

import com.waitless.benefit.point.application.dto.result.GetPointListResult;
import com.waitless.benefit.point.application.dto.command.PageCommand;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record GetPointListResponseDto(
        List<Item> points,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static GetPointListResponseDto from(PageCommand<GetPointListResult> command) {
        List<Item> items = command.content().stream()
                .map(Item::from)
                .toList();
        return new GetPointListResponseDto(
                items,
                command.pageNumber(),
                command.pageSize(),
                command.totalElements(),
                command.totalPages()
        );
    }
    @Builder
    public record Item(
            UUID pointId,
            UUID reviewId,
            UUID reservationId,
            int amount,
            String type,
            String description,
            LocalDateTime createdAt
    ) {
        public static Item from(GetPointListResult result) {
            return Item.builder()
                    .pointId(result.pointId())
                    .reviewId(result.reviewId())
                    .reservationId(result.reservationId())
                    .amount(result.amount().getPointValue())
                    .type(result.type().getPointType().name())
                    .description(result.description())
                    .createdAt(result.createdAt())
                    .build();
        }
    }
}