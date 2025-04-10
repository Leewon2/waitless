package com.waitless.review.application.service;

import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.application.mapper.ReviewServiceMapper;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceMapper reviewServiceMapper;

    @Override

    public PostReviewResult createReview(PostReviewCommand command) {
        Review review = reviewServiceMapper.toEntity(command);
        Review saved = reviewRepository.save(review);
        return PostReviewResult.from(saved);
    }
}
