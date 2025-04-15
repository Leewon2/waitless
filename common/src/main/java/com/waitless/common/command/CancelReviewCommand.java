package com.waitless.common.command;

import java.util.UUID;

public record CancelReviewCommand(
        UUID reviewId,
        Long userId
) {
}
