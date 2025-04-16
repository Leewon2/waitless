package com.waitless.message.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_failed_slack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FailedSlackMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String receiverId;

    @Column(nullable = false)
    private String message;

    private int mySequence;

    private int retryCount = 0;

    private boolean retryCompleted = false;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public FailedSlackMessage(String receiverId, String message, int mySequence, int retryCount, boolean retryCompleted) {
        this.receiverId = receiverId;
        this.message = message;
        this.mySequence = mySequence;
        this.retryCount = retryCount;
        this.retryCompleted = retryCompleted;
    }

    public void success() {
        this.retryCompleted = true;
    }

    public void increaseRetryCount() {
        this.retryCount += 1;
    }
}
