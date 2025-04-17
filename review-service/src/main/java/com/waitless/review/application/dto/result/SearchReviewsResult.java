package com.waitless.review.application.dto.result;

import com.waitless.review.application.dto.command.PageCommand;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.vo.Rating;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Builder
public record SearchReviewsResult(
        UUID reviewId,
        Long userId,
        UUID restaurantId,
        String content,
        Rating rating
) {
    public static SearchReviewsResult from(Review review) {
        return SearchReviewsResult.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .restaurantId(review.getRestaurantId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
    public static PageCommand<SearchReviewsResult> toPageCommand(Page<Review> page) {
        return PageCommand.from(page.map(SearchReviewsResult::from));
    }
}
