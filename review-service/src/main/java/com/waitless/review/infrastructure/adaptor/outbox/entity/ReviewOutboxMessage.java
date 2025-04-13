package com.waitless.review.infrastructure.adaptor.outbox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewOutboxMessage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false)
    private String aggregateType; // REVIEW

    @Column(nullable = false)
    private String type; // 이벤트 타입: review-created

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String payload; // JSON 형태의 이벤트 본문

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status; // PENDING, SENT, FAILED

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
