package com.waitless.common.event;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Builder
public class ReviewDeletedEvent extends Event {
    private UUID reviewId;
    private Long userId;
}
