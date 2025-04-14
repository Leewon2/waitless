package com.waitless.benefit.point.infrastructure.adaptor.outbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "point_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PointOutboxMessage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String aggregateType; // POINT

    @Column(nullable = false)
    private String type; // 예: point-issued, point-revoked

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public enum OutboxStatus {
        PENDING, SENT, FAILED
    }

    public void markAsSent() {
        this.status = OutboxStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }
}
