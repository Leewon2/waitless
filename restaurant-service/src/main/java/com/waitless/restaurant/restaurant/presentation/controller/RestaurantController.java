package com.waitless.restaurant.restaurant.presentation.controller;


import com.waitless.common.exception.response.SingleResponse;
import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.service.RestaurantService;
import com.waitless.restaurant.restaurant.presentation.dto.CreateRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.mapper.RestaurantControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantControllerMapper restaurantControllerMapper;

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody CreateRestaurantRequestDto requestDto) {
        CreateRestaurantResponseDto responseDto = restaurantService.createRestaurant(
            restaurantControllerMapper.toServiceDto(requestDto)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }
}
