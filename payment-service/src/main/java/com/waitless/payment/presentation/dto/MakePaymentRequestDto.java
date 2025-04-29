package com.waitless.payment.presentation.dto;

import java.util.UUID;

public record MakePaymentRequestDto(
	UUID reservationId, UUID couponHistoryId, Integer usedPoint
) {
}
