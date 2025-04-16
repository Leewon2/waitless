package com.waitless.restaurant.restaurant.presentation.controller.internal;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.service.RestaurantService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants/app")
@RequiredArgsConstructor
public class RestaurantInternalController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantResponseDto getRestaurantById(@PathVariable UUID id) {
        return restaurantService.getRestaurant(id);
    }

    @PostMapping("/stock")
    public List<RestaurantStockResponseDto> getRestaurantStock(@RequestBody(required = false) List<UUID> restaurantIdList) {
        return restaurantService.getRestaurantStock(restaurantIdList);
    }
}
