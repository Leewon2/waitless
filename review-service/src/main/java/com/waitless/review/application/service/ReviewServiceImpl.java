package com.waitless.review.application.service;

import com.waitless.common.command.CancelReviewCommand;
import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;
import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.DeleteReviewResult;
import com.waitless.review.application.dto.result.PostReviewResult;
import com.waitless.review.application.mapper.ReviewServiceMapper;
import com.waitless.review.application.port.in.ReviewCommandUseCase;
import com.waitless.review.application.port.out.ReviewOutboxPort;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService, ReviewCommandUseCase {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceMapper reviewServiceMapper;
    private final ReviewOutboxPort reviewOutboxPort;

    @Override
    @Transactional
    public PostReviewResult createReview(PostReviewCommand command) {
        Review review = reviewServiceMapper.toEntity(command);
        Review saved = reviewRepository.save(review);

        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .reviewId(saved.getId())
                .userId(saved.getUserId())
                .restaurantId(saved.getRestaurantId())
                .build();
        reviewOutboxPort.saveReviewCreatedEvent(event);
        return PostReviewResult.from(saved);
    }

    @Override
    @Transactional
    public DeleteReviewResult deleteReview(DeleteReviewCommand command) {
        Review review = reviewRepository.findById(command.reviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        if (!review.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        review.softDelete();

        ReviewDeletedEvent event = ReviewDeletedEvent.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .build();
        reviewOutboxPort.saveReviewDeletedEvent(event);
        return DeleteReviewResult.from(review.getId());
    }

    @Override
    @Transactional
    public void cancelReview(CancelReviewCommand command) {
        log.warn("리뷰 보상 트랜잭션 롤백 요청: reviewId={}, userId={}", command.reviewId(), command.userId());
        Review review = reviewRepository.findById(command.reviewId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다: " + command.reviewId()));

        if (!review.getUserId().equals(command.userId())) {
            throw new IllegalArgumentException("리뷰 작성자와 일치하지 않습니다");
        }
        review.softDelete();
        log.info("리뷰 롤백 완료: reviewId={}", command.reviewId());
    }


}
