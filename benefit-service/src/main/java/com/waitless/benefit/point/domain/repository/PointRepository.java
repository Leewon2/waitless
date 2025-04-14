package com.waitless.benefit.point.domain.repository;

import com.waitless.benefit.point.domain.entity.Point;

import java.util.UUID;

public interface PointRepository {
    Point save(Point point);
    boolean existsByUserIdAndReviewId(Long userId, UUID reviewId);
}
