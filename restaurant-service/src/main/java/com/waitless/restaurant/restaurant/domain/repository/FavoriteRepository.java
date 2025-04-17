package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository {

   Favorite save(Favorite favorite);

   Optional<Favorite> findByRestaurantAndUserId(UUID restaurantId, Long userId);

}
