package com.waitless.review.application.service;

import com.waitless.common.command.CancelReviewCommand;
import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;
import com.waitless.review.application.dto.client.VisitedReservationRequestDto;
import com.waitless.review.application.dto.client.VisitedReservationResponseDto;
import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PageCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.command.ReviewStatisticsCommand;
import com.waitless.review.application.dto.result.*;
import com.waitless.review.application.mapper.ReviewServiceMapper;
import com.waitless.review.application.port.in.ReviewCommandUseCase;
import com.waitless.review.application.port.out.ReviewOutboxPort;
import com.waitless.review.application.port.out.ReviewStatisticsCachePort;
import com.waitless.review.application.port.out.VisitedReservationPort;
import com.waitless.review.application.validator.VisitedReservationValidator;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import com.waitless.review.domain.repository.ReviewRepositoryCustom;
import com.waitless.review.domain.repository.ReviewStatisticsProjection;
import com.waitless.review.domain.vo.ReviewSearchCondition;
import com.waitless.review.domain.vo.ReviewStatisticsCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService, ReviewCommandUseCase {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceMapper reviewServiceMapper;
    private final ReviewOutboxPort reviewOutboxPort;
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final VisitedReservationValidator visitedReservationValidator;
    private final ReviewStatisticsCachePort reviewStatisticsCachePort;

    private static final long TTL_SECONDS = 300;
    private static final String LOG_PREFIX = "[ReviewStatisticsCache]";

    @Override
    @Transactional
    public PostReviewResult createReview(PostReviewCommand command) {
        visitedReservationValidator.validate(command);
        Review review = reviewServiceMapper.toEntity(command);
        Review saved = reviewRepository.save(review);

        reviewStatisticsCachePort.delete(saved.getRestaurantId().toString());

        ReviewCreatedEvent event = ReviewCreatedEvent.builder()
                .reviewId(saved.getId())
                .reservationId(saved.getReservationId())
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
        reviewStatisticsCachePort.delete(review.getRestaurantId().toString());

        ReviewDeletedEvent event = ReviewDeletedEvent.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .build();
        reviewOutboxPort.saveReviewDeletedEvent(event);
        return DeleteReviewResult.from(review);
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

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewStatisticsResult> getStatistics(ReviewStatisticsCommand command) {
        String restaurantId = command.restaurantId().toString();

        // 1. 캐시 조회
        ReviewStatisticsCache cached = reviewStatisticsCachePort.find(restaurantId);
        if (cached != null) {
            log.debug("{} HIT → restaurantId={}, count={}, avg={}", LOG_PREFIX, restaurantId, cached.getReviewCount(), cached.getAverageRating());
            return Optional.of(ReviewStatisticsResult.from(cached.getReviewCount(), cached.getAverageRating()));
        }
        // 2. DB 조회
        Optional<ReviewStatisticsProjection> projectionOpt = reviewRepositoryCustom.findStatisticsByRestaurantId(command.restaurantId());
        if (projectionOpt.isEmpty()) {
            log.warn("{} MISS → 통계 없음: restaurantId={}", LOG_PREFIX, restaurantId);
            return Optional.empty();
        }
        ReviewStatisticsProjection projection = projectionOpt.get();
        long count = projection.getReviewCount();
        double avg = projection.getAverageRating();
        // 3. 캐시에 저장
        ReviewStatisticsCache data = ReviewStatisticsCache.builder()
                .reviewCount(count)
                .averageRating(avg)
                .build();
        reviewStatisticsCachePort.save(restaurantId, data, TTL_SECONDS);
        log.debug("{} MISS → DB 조회 후 캐시 저장: restaurantId={}, count={}, avg={}", LOG_PREFIX, restaurantId, count, avg);
        return Optional.of(ReviewStatisticsResult.from(count, avg));
    }

    @Override
    @Transactional(readOnly = true)
    public GetReviewResult findOne(ReviewSearchCondition condition) {
        Review review = reviewRepositoryCustom.findOneByCondition(condition)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
        return GetReviewResult.from(review);
    }

    @Override
    @Transactional(readOnly = true)
    public PageCommand<GetReviewListResult> findList(ReviewSearchCondition condition, Pageable pageable) {
        Page<Review> reviewPage = reviewRepositoryCustom.searchByCondition(condition, pageable);
        return GetReviewListResult.toPageCommand(reviewPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageCommand<SearchReviewsResult> findSearch(ReviewSearchCondition condition, Pageable pageable) {
        Page<Review> reviewPage = reviewRepositoryCustom.searchByCondition(condition, pageable);
        return SearchReviewsResult.toPageCommand(reviewPage);
    }
}
