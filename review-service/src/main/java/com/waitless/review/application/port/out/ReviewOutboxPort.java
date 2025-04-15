package com.waitless.review.application.port.out;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;

public interface ReviewOutboxPort {
    void saveReviewCreatedEvent(ReviewCreatedEvent event);
    void saveReviewDeletedEvent(ReviewDeletedEvent event);
}
