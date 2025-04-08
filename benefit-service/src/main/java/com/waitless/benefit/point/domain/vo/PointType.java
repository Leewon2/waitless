package com.waitless.benefit.point.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class PointType {

    @Enumerated(EnumType.STRING)
    private Type value;

    protected PointType() {}

    private PointType(Type value) {
        this.value = value;
    }

    public static PointType of(Type value) {
        return new PointType(value);
    }

    public boolean isEventType() {
        return this.value == Type.EVENT;
    }

    public enum Type {
        REVIEW_REWARD,     // 리뷰 작성
        ATTENDANCE_BONUS,  // 출석 체크
        EVENT              // 실시간 이벤트 등
    }
}
