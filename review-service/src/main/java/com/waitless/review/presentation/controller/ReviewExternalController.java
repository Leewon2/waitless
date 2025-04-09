package com.waitless.review.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.PostReviewRequestDto;
import com.waitless.review.presentation.dto.response.PostReviewResponseDto;
import com.waitless.review.presentation.mapper.ReviewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewExternalController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<SingleResponse<PostReviewResponseDto>> createReview(
            @Valid @RequestBody PostReviewRequestDto requestDto) {
        PostReviewCommand command = reviewMapper.toCommand(requestDto);
        PostReviewResponseDto responseDto = PostReviewResponseDto.from(
                reviewService.createReview(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }
}
