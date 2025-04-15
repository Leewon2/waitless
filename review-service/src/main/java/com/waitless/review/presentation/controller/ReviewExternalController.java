package com.waitless.review.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.DeleteReviewResult;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.DeleteReviewRequestDto;
import com.waitless.review.presentation.dto.request.PostReviewRequestDto;
import com.waitless.review.presentation.dto.response.DeleteReviewResponseDto;
import com.waitless.review.presentation.dto.response.PostReviewResponseDto;
import com.waitless.review.presentation.mapper.ReviewControllerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        DeleteReviewResult result = reviewService.deleteReview(command);
        return ResponseEntity.ok(SingleResponse.success(DeleteReviewResponseDto.from(result)));
    }
}
