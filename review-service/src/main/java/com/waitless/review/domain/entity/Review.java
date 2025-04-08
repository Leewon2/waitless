package com.waitless.review.domain.entity;

import com.waitless.review.domain.vo.Rating;
import com.waitless.review.domain.vo.ReviewType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Review {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private UUID restaurantId;

    @Embedded
    private Rating rating;

    @Column(length = 255, nullable = false)
    private String content;

    @Embedded
    private ReviewType type;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public static Review of(Long userId, UUID restaurantId, String content, Rating rating) {
        return Review.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .restaurantId(restaurantId)
                .content(content)
                .rating(rating)
                .type(ReviewType.of(ReviewType.Type.NORMAL))
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void update(String newContent, Rating newRating) {
        this.content = newContent;
        this.rating = newRating;
        this.type = ReviewType.of(ReviewType.Type.EDITED);
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.type = ReviewType.of(ReviewType.Type.DELETED);
    }
}
