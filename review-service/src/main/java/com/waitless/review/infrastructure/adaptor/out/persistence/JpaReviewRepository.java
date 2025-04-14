package com.waitless.review.infrastructure.adaptor.out.persistence;

import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.repository.ReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaReviewRepository extends JpaRepository<Review, UUID>, ReviewRepository {
}
