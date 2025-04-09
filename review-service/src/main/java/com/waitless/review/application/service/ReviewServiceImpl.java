package com.waitless.review.application.service;

import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import com.waitless.review.domain.vo.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public PostReviewResult createReview(PostReviewCommand command) {
        Review review = Review.of(
                command.userId(),
                command.restaurantId(),
                command.content(),
                Rating.of(command.rating())
        );
        Review saved = reviewRepository.save(review);
        return PostReviewResult.from(saved);
    }
}
