package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantWithMenuResponseDto;
import com.waitless.restaurant.restaurant.application.dto.SearchRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.UpdateRestaurantDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    RestaurantResponseDto createRestaurant(CreateRestaurantDto requestDto);

    RestaurantResponseDto updateRestaurant(UUID id, UpdateRestaurantDto serviceDto);

    RestaurantWithMenuResponseDto getRestaurantWithMenu(UUID id);

    RestaurantResponseDto deleteRestaurant(UUID id);

    Page<RestaurantResponseDto> getRestaurantList(SearchRestaurantDto serviceDto, Pageable pageable);
}
