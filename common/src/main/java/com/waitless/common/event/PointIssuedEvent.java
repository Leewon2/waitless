package com.waitless.common.event;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Builder
public class PointIssuedEvent extends Event{
    private UUID pointId;
    private Long userId;
    private Integer amount;
    private String description;
    private LocalDateTime issuedAt;
}
