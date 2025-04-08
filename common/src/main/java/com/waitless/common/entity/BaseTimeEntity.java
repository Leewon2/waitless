package com.waitless.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    @PrePersist
    public void setCreatedAt() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdateAt() {
        updatedAt = LocalDateTime.now();
    }

}
