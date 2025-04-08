package com.waitless.review.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class ReviewType {

    @Enumerated(EnumType.STRING)
    private Type value;

    protected ReviewType() {}

    private ReviewType(Type value) {
        this.value = value;
    }

    public static ReviewType of(Type value) {
        return new ReviewType(value);
    }

    public boolean isDeleted() {
        return this.value == Type.DELETED;
    }

    public enum Type {
        NORMAL,     // 최초 작성
        EDITED,     // 수정됨
        DELETED     // 삭제됨
    }
}
