package com.waitless.review.application.validator;

import com.waitless.review.application.dto.command.PostReviewCommand;

public interface VisitedReservationValidator {
    void validate(PostReviewCommand command);
}