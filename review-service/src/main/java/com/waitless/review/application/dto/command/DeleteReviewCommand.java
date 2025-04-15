package com.waitless.review.application.dto.command;

import java.util.UUID;

public record DeleteReviewCommand(
        UUID reviewId,
        Long userId
) {}
