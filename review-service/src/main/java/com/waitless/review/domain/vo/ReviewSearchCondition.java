package com.waitless.review.domain.vo;

import java.util.UUID;

public record ReviewSearchCondition(
        UUID reviewId,  // 단건 조회에서만
        Long userId,
        UUID restaurantId,
        Integer rating
) {}