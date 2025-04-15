package com.waitless.common.dto;

import java.util.List;
import java.util.UUID;

public record RestaurantStockResponseDto(UUID restaurantId,
                                         int maxTableCount,
                                         List<StockDto> stockList) {
}
