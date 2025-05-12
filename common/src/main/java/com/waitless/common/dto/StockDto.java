package com.waitless.common.dto;

import java.util.UUID;

public record StockDto(UUID menuId, int amount, int price) {

}
