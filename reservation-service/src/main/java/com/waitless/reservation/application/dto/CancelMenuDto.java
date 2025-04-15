package com.waitless.reservation.application.dto;

import java.util.UUID;

public record CancelMenuDto(UUID menuId, Integer quantity) {
}
