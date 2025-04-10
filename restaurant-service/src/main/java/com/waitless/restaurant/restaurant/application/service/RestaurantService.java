package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantResponseDto;

public interface RestaurantService {
    CreateRestaurantResponseDto createRestaurant(CreateRestaurantDto requestDto);

}
