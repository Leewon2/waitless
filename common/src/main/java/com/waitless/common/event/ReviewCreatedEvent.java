package com.waitless.common.event;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Builder
public class ReviewCreatedEvent extends Event {
    private UUID reviewId;
    private UUID reservationId;
    private Long userId;
    private UUID restaurantId;
}
