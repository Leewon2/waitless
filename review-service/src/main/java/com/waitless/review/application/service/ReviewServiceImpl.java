package com.waitless.review.application.service;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.application.mapper.ReviewServiceMapper;
import com.waitless.review.application.port.out.ReviewEventPublisher;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceMapper reviewServiceMapper;
    private final ReviewEventPublisher reviewEventPublisher;

    @Override
    @Transactional
    public PostReviewResult createReview(PostReviewCommand command) {
        Review review = reviewServiceMapper.toEntity(command);
        Review saved = reviewRepository.save(review);

        ReviewCreatedEvent event = ReviewCreatedEvent.of(
                saved.getId(),
                saved.getUserId(),
                saved.getRestaurantId()
        );
        reviewEventPublisher.publishReviewCreated(event);
        return PostReviewResult.from(saved);
    }
}
