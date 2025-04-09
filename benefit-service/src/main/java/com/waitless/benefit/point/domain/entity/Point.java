package com.waitless.benefit.point.domain.entity;

import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import com.waitless.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_point")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Point extends BaseTimeEntity {

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

    public static Point of(Long userId, PointAmount amount, PointType type, String description) {
        return Point.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .amount(amount)
                .type(type)
                .description(description)
                .build();
    }

    public void softDelete() {
        this.delete();
    }
}
