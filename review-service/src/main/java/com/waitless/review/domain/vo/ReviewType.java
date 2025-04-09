package com.waitless.review.domain.vo;

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
public class ReviewType {

    @Enumerated(EnumType.STRING)
    @Column(name = "review_type")
    private Type reviewType;

    private ReviewType(Type reviewType) {
        this.reviewType = reviewType;
    }

    public static ReviewType of(Type reviewType) {
        return new ReviewType(reviewType);
    }

    public boolean isTypeDeleted() {
        return this.reviewType == Type.DELETED;
    }

    public boolean isTypeEditied(){
        return this.reviewType == Type.EDITED;
    }

    public enum Type {
        NORMAL,     // 최초 작성
        EDITED,     // 수정됨
        DELETED     // 삭제됨
    }
}
