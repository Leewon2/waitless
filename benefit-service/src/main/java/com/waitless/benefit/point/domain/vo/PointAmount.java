package com.waitless.benefit.point.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PointAmount {

    private int value;

    protected PointAmount() {}

    private PointAmount(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("포인트 양은 0보다 적을 수 없습니다.");
        }
        this.value = value;
    }

    public static PointAmount of(int value) {
        return new PointAmount(value);
    }
}
