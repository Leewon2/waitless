package com.waitless.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FilterDef(
        name = "deletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = Boolean.class)
)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }

    public void revert() {this.isDeleted = false;}
    public boolean isDeleted() {
        return this.isDeleted;
    }
}
