package com.waitless.review.domain.repository;

import com.waitless.review.domain.entity.Review;

public interface ReviewRepository {
    Review save(Review review);
}
