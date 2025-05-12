package com.waitless.review.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating {

    @Column(name = "rating_value")
    private Integer ratingValue;

    private Rating(Integer ratingValue) {
        if (ratingValue == null || ratingValue < 1 || ratingValue > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.ratingValue = ratingValue;
    }

    public static Rating of(Integer ratingValue) {
        return new Rating(ratingValue);
    }
}
