package com.waitless.review.application.port.out;

import com.waitless.common.event.ReviewCreatedEvent;
import com.waitless.common.event.ReviewDeletedEvent;

public interface ReviewEventPublisher {
    void publishReviewCreated(ReviewCreatedEvent event);
    void publishReviewDeleted(ReviewDeletedEvent event);
}
