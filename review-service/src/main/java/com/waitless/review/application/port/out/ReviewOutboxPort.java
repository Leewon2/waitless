package com.waitless.review.application.port.out;

import com.waitless.common.event.ReviewCreatedEvent;

public interface ReviewOutboxPort {
    void saveReviewCreatedEvent(ReviewCreatedEvent event);
}
