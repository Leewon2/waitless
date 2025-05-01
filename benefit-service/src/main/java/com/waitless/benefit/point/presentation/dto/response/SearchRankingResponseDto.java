package com.waitless.benefit.point.presentation.dto.response;

import com.waitless.benefit.point.application.dto.result.SearchRankingResult;
import com.waitless.benefit.point.application.dto.command.PageCommand;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchRankingResponseDto(
        List<Item> rankings,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static SearchRankingResponseDto from(PageCommand<SearchRankingResult> command) {
        List<Item> items = command.content().stream()
                .map(Item::from)
                .toList();
        return new SearchRankingResponseDto(
                items,
                command.pageNumber(),
                command.pageSize(),
                command.totalElements(),
                command.totalPages()
        );
    }
    @Builder
    public record Item(
            Long userId,
            int totalPoint,
            int rank
    ) {
        public static Item from(SearchRankingResult result) {
            return Item.builder()
                    .userId(result.userId())
                    .totalPoint(result.totalPoint())
                    .rank(result.rank())
                    .build();
        }
    }
}