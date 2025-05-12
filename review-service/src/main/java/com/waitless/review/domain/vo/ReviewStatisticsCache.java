package com.waitless.review.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatisticsCache implements Serializable {
    private double averageRating;
    private long reviewCount;
}