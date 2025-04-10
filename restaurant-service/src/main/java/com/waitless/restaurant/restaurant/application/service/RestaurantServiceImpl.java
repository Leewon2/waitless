package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.mapper.RestaurantServiceMapper;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryService categoryService;
    private final RestaurantServiceMapper restaurantServiceMapper;

    @Transactional
    public CreateRestaurantResponseDto createRestaurant(CreateRestaurantDto createRestaurantDto) {

        //todo:ownerId 유효성 검사

        Category category = categoryService.findById(createRestaurantDto.categoryId());

        Restaurant restaurant = Restaurant.of(
           createRestaurantDto.name(),
           createRestaurantDto.ownerId(),
           createRestaurantDto.phone(),
           category,
           createRestaurantDto.toLocation(),
           createRestaurantDto.toOperatingHours()
        );

        restaurantRepository.save(restaurant);
        return restaurantServiceMapper.toResponseDto(restaurant);
    }
}
