package com.waitless.review.domain.repository;

public interface ReviewStatisticsProjection {
    long getReviewCount();
    double getAverageRating();
}