package com.waitless.payment.application.dto;

import java.util.UUID;

public record MakePaymentDto(
	UUID reservationId, UUID couponHistoryId, Integer usedPoint
) {
}
