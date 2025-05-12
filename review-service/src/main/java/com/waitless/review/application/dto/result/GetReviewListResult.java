package com.waitless.review.application.dto.result;

import com.waitless.review.application.dto.command.PageCommand;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.vo.Rating;
import com.waitless.review.domain.vo.ReviewType;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record GetReviewListResult(
        UUID reviewId,
        Long userId,
        UUID restaurantId,
        String content,
        Rating rating,
        ReviewType type,
        LocalDateTime createdAt
) {
    public static GetReviewListResult from(Review review) {
        return GetReviewListResult.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .restaurantId(review.getRestaurantId())
                .content(review.getContent())
                .rating(review.getRating())
                .type(review.getType())
                .createdAt(review.getCreatedAt())
                .build();
    }
    public static PageCommand<GetReviewListResult> toPageCommand(Page<Review> page) {
        return PageCommand.from(page.map(GetReviewListResult::from));
    }
}
