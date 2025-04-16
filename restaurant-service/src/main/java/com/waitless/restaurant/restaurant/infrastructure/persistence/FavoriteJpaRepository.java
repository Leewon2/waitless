package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteJpaRepository extends JpaRepository<Favorite, UUID> {

    Optional<Favorite> findByRestaurant_IdAndUserId(UUID restaurantId, Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.userId = :userId AND f.isDeleted = false")
    Page<Favorite> findAllByUserId(Long userId, Pageable pageable);

}
