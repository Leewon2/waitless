package com.waitless.benefit.point.domain.entity;

import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Point {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Embedded
    private PointAmount amount;

    @Embedded
    private PointType type;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static Point of(Long userId, PointAmount amount, PointType type, String description) {
        return Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .amount(amount)
                .type(type)
                .description(description)
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
