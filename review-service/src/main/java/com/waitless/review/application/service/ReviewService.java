package com.waitless.review.application.service;

import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.result.PostReviewResult;

public interface ReviewService {
    PostReviewResult createReview(PostReviewCommand command);
}
