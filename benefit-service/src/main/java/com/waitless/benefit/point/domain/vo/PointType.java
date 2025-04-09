package com.waitless.benefit.point.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointType {

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type")
    private Type pointType;

    private PointType(Type pointType) {
        this.pointType = pointType;
    }

    public static PointType of(Type pointType) {
        return new PointType(pointType);
    }
    public boolean isEventType() {
        return this.pointType == Type.EVENT;
    }
    public boolean isReviewType(){
        return this.pointType == Type.REVIEW_REWARD;
    }
    public boolean isAttendanceType(){
        return this.pointType == Type.ATTENDANCE_BONUS;
    }

    public enum Type {
        REVIEW_REWARD,     // 리뷰 작성
        ATTENDANCE_BONUS,  // 출석 체크
        EVENT              // 실시간 이벤트 등
    }
}
