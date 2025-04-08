package com.waitless.review.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Rating {

    private Integer value;

    protected Rating() {}

    private Rating(Integer value) {
        if (value == null || value < 1 || value > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.value = value;
    }

    public static Rating of(Integer value) {
        return new Rating(value);
    }
}
