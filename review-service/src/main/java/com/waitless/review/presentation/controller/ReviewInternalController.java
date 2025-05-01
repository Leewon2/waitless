package com.waitless.review.presentation.controller;

import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.domain.UserInfo;
import com.waitless.common.domain.UserInfoDto;
import com.waitless.review.application.dto.command.ReviewStatisticsCommand;
import com.waitless.review.application.dto.result.ReviewStatisticsResult;
import com.waitless.review.application.service.ReviewService;
import com.waitless.review.presentation.dto.request.ReviewStatisticsBatchRequestDto;
import com.waitless.review.presentation.dto.response.ReviewStatisticsBatchResponseDto;
import com.waitless.review.presentation.mapper.ReviewControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews/app/statistics")
public class ReviewInternalController {

    private final ReviewService reviewService;
    private final ReviewControllerMapper reviewControllerMapper;

    @RoleCheck(roles = {Role.OWNER, Role.ADMIN})
    @PostMapping
    public List<ReviewStatisticsBatchResponseDto> getStatisticsBatch(
            @RequestBody ReviewStatisticsBatchRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        ReviewStatisticsCommand command = reviewControllerMapper.toCommand(requestDto, userInfoDto);
        Map<UUID, ReviewStatisticsResult> resultMap = reviewService.getStatisticsBatch(command);
        return ReviewStatisticsBatchResponseDto.fromMap(resultMap);
    }
}