package com.waitless.restaurant.restaurant.application.service;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantWithMenuResponseDto;
import com.waitless.restaurant.restaurant.application.dto.SearchRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.UpdateRestaurantDto;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import com.waitless.restaurant.restaurant.application.exception.RestaurantErrorCode;
import com.waitless.restaurant.restaurant.application.mapper.RestaurantServiceMapper;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantQueryRepository;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantRepository;
import com.waitless.restaurant.restaurant.domain.vo.Location;
import com.waitless.restaurant.restaurant.domain.vo.OperatingHours;
import com.waitless.restaurant.restaurant.infrastructure.adaptor.in.client.ReservationInternalClient;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantServiceMapper restaurantServiceMapper;
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final ReservationInternalClient reservationInternalClient;

    @Transactional
    public RestaurantResponseDto createRestaurant(CreateRestaurantDto createRestaurantDto) {

        //todo:ownerId 유효성 검사

        Category category = categoryService.findById(createRestaurantDto.categoryId());

        Restaurant restaurant = Restaurant.of(
            createRestaurantDto.name(),
            createRestaurantDto.ownerId(),
            createRestaurantDto.phone(),
            category,
            createRestaurantDto.maxTableCount(),
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
        menuService.deleteAllMenusByRestaurantId(id);

        Restaurant restaurant = findById(id);
        restaurant.delete();

        return restaurantServiceMapper.toResponseDto(restaurant);
    }


    @Transactional(readOnly = true)
    public RestaurantWithMenuResponseDto getRestaurantWithMenu(UUID id) {
        Restaurant restaurant = findById(id);

       return restaurantServiceMapper.toWithMenuDto(restaurant, menuService.getMenus(id));
    }

   @Transactional(readOnly = true)
    public Page<RestaurantResponseDto> getRestaurantList(SearchRestaurantDto searchRestaurantDto, Pageable pageable) {
        Page<Restaurant> restaurantList = restaurantQueryRepository.searchRestaurant(searchRestaurantDto.name(),searchRestaurantDto.categoryId(),pageable);

        return restaurantList.map(restaurantServiceMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public List<RestaurantStockResponseDto> getRestaurantStock(List<UUID> restaurantIdList) {

        List<Restaurant> restaurantList;

        if(restaurantIdList == null || restaurantIdList.isEmpty()){
            restaurantList = restaurantRepository.findAll();
        }else {
            restaurantList = restaurantRepository.findByIdIn(restaurantIdList);
        }

        return restaurantList.stream().map(restaurant ->
            restaurantServiceMapper.toStockResponseDto(restaurant, menuService.getMenus(restaurant.getId()))).toList();
    }

    @Transactional
    public RestaurantResponseDto closeRestaurant(UUID id) {
        Restaurant restaurant = findById(id);
        restaurant.close();

        reservationInternalClient.close(id);
        return restaurantServiceMapper.toResponseDto(restaurant);
    }

    @Transactional(readOnly = true)
    public RestaurantResponseDto getRestaurant(UUID id) {
        return restaurantServiceMapper.toResponseDto(findById(id));
    }


    @Transactional(readOnly = true)
    public Restaurant findById(UUID id) {
      return restaurantRepository.findById(id).orElseThrow(()-> RestaurantBusinessException.from(
          RestaurantErrorCode.RESTAURANT_NOT_FOUND));
    }
}
