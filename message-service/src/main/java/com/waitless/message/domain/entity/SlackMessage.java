package com.waitless.message.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_slack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
public class SlackMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String receiverId;

    @Column(nullable = false)
    private String message;

    public SlackMessage(String receiverId, String message) {
        this.receiverId=receiverId;
        this.message = message;
    }
}
