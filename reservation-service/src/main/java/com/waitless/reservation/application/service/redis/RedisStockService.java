package com.waitless.reservation.application.service.redis;

import com.waitless.reservation.application.dto.ReservationCreateCommand;

import java.util.List;
import java.util.UUID;

public interface RedisStockService {
    Long decrementStock(UUID storeId, List<ReservationCreateCommand.MenuCommandDto> menus);
}
