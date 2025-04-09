package com.waitless.benefit.point.domain.vo;

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
public class PointAmount {

    @Column(name = "point_value")
    private Integer pointValue;

    private PointAmount(Integer pointValue) {
        if (pointValue < 0) {
            throw new IllegalArgumentException("포인트 양은 0보다 적을 수 없습니다.");
        }
        this.pointValue = pointValue;
    }

    public static PointAmount of(Integer pointValue) {
        return new PointAmount(pointValue);
    }
}
