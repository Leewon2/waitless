package com.waitless.review.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.domain.vo.ReviewSearchCondition;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.*;
import com.waitless.review.presentation.dto.response.*;
import com.waitless.review.presentation.mapper.ReviewControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewExternalController {

    private final ReviewService reviewService;
    private final ReviewControllerMapper reviewControllerMapper;

    @PostMapping
    public ResponseEntity<SingleResponse<PostReviewResponseDto>> createReview(
            @Valid @RequestBody PostReviewRequestDto requestDto) {
        PostReviewCommand command = reviewControllerMapper.toCommand(requestDto);
        PostReviewResponseDto responseDto = PostReviewResponseDto.from(
                reviewService.createReview(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @DeleteMapping
    public ResponseEntity<SingleResponse<DeleteReviewResponseDto>> deleteReview(
            @RequestBody DeleteReviewRequestDto requestDto
    ) {
        DeleteReviewCommand command = reviewControllerMapper.toCommand(requestDto);
        DeleteReviewResponseDto responseDto = DeleteReviewResponseDto.from(
                reviewService.deleteReview(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<SingleResponse<GetReviewResponseDto>> getReview(
            @PathVariable("reviewId") UUID reviewId
    ) {
        GetReviewRequestDto requestDto = new GetReviewRequestDto(reviewId);
        ReviewSearchCondition condition = reviewControllerMapper.toCondition(requestDto);
        GetReviewResponseDto responseDto = GetReviewResponseDto.from(
                reviewService.findOne(condition)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/list")
    public ResponseEntity<SingleResponse<GetReviewListResponseDto>> getReviewList(
            @ModelAttribute GetReviewListRequestDto requestDto,
            Pageable pageable
    ) {
        ReviewSearchCondition condition = reviewControllerMapper.toCondition(requestDto);
        GetReviewListResponseDto responseDto = GetReviewListResponseDto.from(
                reviewService.findList(condition, pageable)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/search")
    public ResponseEntity<SingleResponse<SearchReviewsResponseDto>> searchReviews(
            @ModelAttribute SearchReviewsRequestDto requestDto,
            Pageable pageable
    ) {
        ReviewSearchCondition condition = reviewControllerMapper.toCondition(requestDto);
        SearchReviewsResponseDto responseDto = SearchReviewsResponseDto.from(
                reviewService.findSearch(condition, pageable)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }
}
