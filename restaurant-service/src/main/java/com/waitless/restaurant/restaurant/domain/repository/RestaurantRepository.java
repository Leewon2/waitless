package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);
}
