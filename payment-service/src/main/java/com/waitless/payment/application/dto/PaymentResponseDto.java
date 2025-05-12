package com.waitless.payment.application.dto;

import java.util.UUID;

public record PaymentResponseDto(UUID id, int price, int discountedPrice) {
}
