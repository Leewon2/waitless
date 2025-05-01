package com.waitless.review.application.dto.command;

import com.waitless.common.domain.UserInfoDto;

import java.util.UUID;

public record DeleteReviewCommand(
        UUID reviewId,
        Long userId,
        UserInfoDto userInfoDto
) {}
