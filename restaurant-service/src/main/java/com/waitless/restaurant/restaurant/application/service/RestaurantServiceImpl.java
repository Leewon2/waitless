package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.UpdateRestaurantDto;
import com.waitless.restaurant.restaurant.application.mapper.RestaurantServiceMapper;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantRepository;
import com.waitless.restaurant.restaurant.domain.vo.Location;
import com.waitless.restaurant.restaurant.domain.vo.OperatingHours;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantServiceMapper restaurantServiceMapper;
    private final CategoryService categoryService;

    @Transactional
    public RestaurantResponseDto createRestaurant(CreateRestaurantDto createRestaurantDto) {

        //todo:ownerId 유효성 검사

        Category category = categoryService.findById(createRestaurantDto.categoryId());

        Restaurant restaurant = Restaurant.of(
            createRestaurantDto.name(),
            createRestaurantDto.ownerId(),
            createRestaurantDto.phone(),
            category,
            Location.of(createRestaurantDto.latitude(), createRestaurantDto.longitude()),
            OperatingHours.of(createRestaurantDto.openingTime(),createRestaurantDto.closingTime())
        );

        restaurantRepository.save(restaurant);
        return restaurantServiceMapper.toResponseDto(restaurant);
    }

    @Transactional
    public RestaurantResponseDto updateRestaurant(UUID id, UpdateRestaurantDto updateRestaurantDto) {
        Restaurant restaurant = findById(id);
        restaurant.update(updateRestaurantDto.phone(), updateRestaurantDto.openingTime(), updateRestaurantDto.closingTime());

        return restaurantServiceMapper.toResponseDto(restaurant);
    }

    @Transactional
    public RestaurantResponseDto deleteRestaurant(UUID id) {

        Restaurant restaurant = findById(id);
        restaurant.delete();

        return restaurantServiceMapper.toResponseDto(restaurant);
    }

    @Transactional(readOnly = true)
    public Restaurant findById(UUID id) {
      return restaurantRepository.findById(id).orElseThrow(()-> new NullPointerException("식당을 찾을 수 없습니다."));
    }

}
