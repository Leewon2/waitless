package com.waitless.common.event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsedEvent {
	private UUID couponHistoryId;
	private Long userId;
}
