package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepository {

   Favorite save(Favorite favorite);

   Optional<Favorite> findByRestaurantAndUserId(UUID restaurantId, Long userId);

   Optional<Favorite> findById(UUID id);

   Page<Favorite> findAllByUserId(Long userId, Pageable pageable);
}
