package com.waitless.reservation.application.service.redis;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.reservation.application.dto.CancelMenuDto;
import com.waitless.reservation.application.dto.ReservationCreateCommand;

import java.util.List;
import java.util.UUID;

public interface RedisStockService {
    Long decrementStock(UUID storeId, List<ReservationCreateCommand.MenuCommandDto> menus);

    void incrementStock(UUID restaurantId, List<CancelMenuDto> menus);

    void saveStock(List<RestaurantStockResponseDto> restaurantStock);

    void closedRestaurant(UUID restaurantId);
}
