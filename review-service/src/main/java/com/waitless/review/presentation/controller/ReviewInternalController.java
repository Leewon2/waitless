package com.waitless.review.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.review.application.dto.command.ReviewStatisticsCommand;
import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.ReviewStatisticsRequestDto;
import com.waitless.review.presentation.dto.response.ReviewStatisticsResponseDto;
import com.waitless.review.presentation.mapper.ReviewControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/app/statistics")
public class ReviewInternalController {
    private final ReviewService reviewService;
    private final ReviewControllerMapper reviewControllerMapper;
    @GetMapping
    public ResponseEntity<SingleResponse<ReviewStatisticsResponseDto>> getStatistics(
            @ModelAttribute ReviewStatisticsRequestDto requestDto
    ) {
        ReviewStatisticsCommand command = reviewControllerMapper.toCommand(requestDto);
        ReviewStatisticsResult result = reviewService.getStatistics(command)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 통계를 찾을 수 없습니다."));
        return ResponseEntity.ok(SingleResponse.success(ReviewStatisticsResponseDto.from(result)));
    }
}