package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    List<Restaurant> findAll();

    List<Restaurant> findByIdIn(List<UUID> idList);
}
