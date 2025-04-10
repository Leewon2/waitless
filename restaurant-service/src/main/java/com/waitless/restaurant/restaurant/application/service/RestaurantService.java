package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.UpdateRestaurantDto;
import java.util.UUID;

public interface RestaurantService {
    RestaurantResponseDto createRestaurant(CreateRestaurantDto requestDto);

    RestaurantResponseDto updateRestaurant(UUID id, UpdateRestaurantDto serviceDto);
}
