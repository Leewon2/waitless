package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantQueryRepository {

    Page<Restaurant> searchRestaurant(String name, String categoryId, Pageable pageable);
}
