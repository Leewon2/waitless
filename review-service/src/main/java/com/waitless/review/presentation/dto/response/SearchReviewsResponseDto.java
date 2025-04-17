package com.waitless.review.presentation.dto.response;

import com.waitless.review.application.dto.result.SearchReviewsResult;
import com.waitless.review.application.dto.command.PageCommand;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record SearchReviewsResponseDto(
        List<Item> reviews,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static SearchReviewsResponseDto from(PageCommand<SearchReviewsResult> command) {
        List<Item> items = command.content().stream()
                .map(Item::from)
                .toList();

        return new SearchReviewsResponseDto(
                items,
                command.pageNumber(),
                command.pageSize(),
                command.totalElements(),
                command.totalPages()
        );
    }

    @Builder
    public record Item(
            UUID reviewId,
            Long userId,
            UUID restaurantId,
            String content,
            int rating
    ) {
        public static Item from(SearchReviewsResult result) {
            return Item.builder()
                    .reviewId(result.reviewId())
                    .userId(result.userId())
                    .restaurantId(result.restaurantId())
                    .content(result.content())
                    .rating(result.rating().getRatingValue())
                    .build();
        }
    }
}
