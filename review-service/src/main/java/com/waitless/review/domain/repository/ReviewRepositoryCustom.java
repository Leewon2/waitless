package com.waitless.review.domain.repository;

import com.waitless.review.domain.vo.ReviewSearchCondition;
import com.waitless.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Page<Review> searchByCondition(ReviewSearchCondition condition, Pageable pageable);
    Optional<Review> findOneByCondition(ReviewSearchCondition condition);
}