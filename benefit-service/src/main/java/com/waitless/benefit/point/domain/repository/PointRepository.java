package com.waitless.benefit.point.domain.repository;

import com.waitless.benefit.point.domain.entity.Point;

import java.util.Optional;
import java.util.UUID;

public interface PointRepository {
    Point save(Point point);
    boolean existsByUserIdAndReservationId(Long userId, UUID reservationId);
    Optional<Point> findByReviewIdAndUserId(UUID reviewId, Long userId);
}
