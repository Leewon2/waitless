package com.waitless.reservation.infrastructure.adaptor.client;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.reservation.infrastructure.adaptor.client.dto.RestaurantResponseDto;
import com.waitless.reservation.infrastructure.config.feign.FeignCommonConfig;
import com.waitless.reservation.infrastructure.config.feign.RestaurantFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "restaurant-service",
        url = "${gateway.url}",
        configuration = {FeignCommonConfig.class, RestaurantFeignConfig.class}
)
public interface RestaurantClient {

    @GetMapping("/restaurant-service/api/restaurants/{restaurantId}")
    void existRestaurant(@PathVariable UUID restaurantId);

    @GetMapping("/restaurant-service/api/restaurants/app/{restaurantId}")
    RestaurantResponseDto validateOwnerId(@PathVariable UUID restaurantId);

    @PostMapping("/restaurant-service/api/restaurants/app/stock")
    List<RestaurantStockResponseDto> getRestaurantStock(List<UUID> restaurantIdList);
}