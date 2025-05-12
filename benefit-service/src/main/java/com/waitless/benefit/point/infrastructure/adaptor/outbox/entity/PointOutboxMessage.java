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

    @Column(nullable = false)
    @Builder.Default
    private int retryCount = 0;

    private LocalDateTime lastTriedAt;

    public enum OutboxStatus {
        PENDING, SENT, FAILED
    }
    public void markAsSent() {
        this.status = OutboxStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }
    public void markAsFailed() {
        this.status = OutboxStatus.FAILED;
        this.lastTriedAt = LocalDateTime.now();
    }
    public void incrementRetry() {
        this.retryCount++;
        this.lastTriedAt = LocalDateTime.now();
    }
    public boolean isRetryable(int maxRetry) {
        return this.retryCount < maxRetry;
    }
}
