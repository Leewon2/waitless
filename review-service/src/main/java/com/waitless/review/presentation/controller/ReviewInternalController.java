package com.waitless.review.presentation.controller;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.review.application.dto.command.ReviewStatisticsCommand;
import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.ReviewStatisticsBatchRequestDto;
import com.waitless.review.presentation.dto.response.ReviewStatisticsBatchResponseDto;
import com.waitless.review.presentation.mapper.ReviewControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/app/statistics")
public class ReviewInternalController {

    private final ReviewService reviewService;
    private final ReviewControllerMapper reviewControllerMapper;
    @PostMapping
    public ResponseEntity<SingleResponse<List<ReviewStatisticsBatchResponseDto>>> getStatisticsBatch(
            @RequestBody ReviewStatisticsBatchRequestDto requestDto
    ) {
        ReviewStatisticsCommand command = reviewControllerMapper.toCommand(requestDto);
        Map<UUID, ReviewStatisticsResult> resultMap = reviewService.getStatisticsBatch(command);
        return ResponseEntity.ok(SingleResponse.success(ReviewStatisticsBatchResponseDto.fromMap(resultMap)));
    }
}