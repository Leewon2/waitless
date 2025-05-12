package com.waitless.review.application.port.in;

import com.waitless.common.command.CancelReviewCommand;

public interface ReviewCommandUseCase {
    void cancelReview(CancelReviewCommand command);
}