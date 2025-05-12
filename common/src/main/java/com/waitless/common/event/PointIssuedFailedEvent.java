package com.waitless.common.event;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Builder
public class PointIssuedFailedEvent extends Event {
    private UUID reviewId;
    private Long userId;
    private UUID reservationId;
}
