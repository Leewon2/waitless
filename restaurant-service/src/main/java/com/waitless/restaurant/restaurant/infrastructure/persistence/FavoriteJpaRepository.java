package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteJpaRepository extends JpaRepository<Favorite, UUID> {

    Optional<Favorite> findByRestaurant_IdAndUserId(UUID restaurantId, Long userId);
}
