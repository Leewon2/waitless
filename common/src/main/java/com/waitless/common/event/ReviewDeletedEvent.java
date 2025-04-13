package com.waitless.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ReviewDeletedEvent extends Event {
    private UUID reviewId;
    private Long userId;
}
