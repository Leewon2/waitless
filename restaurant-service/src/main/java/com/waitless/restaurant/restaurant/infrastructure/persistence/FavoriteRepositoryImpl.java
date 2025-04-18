package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import com.waitless.restaurant.restaurant.domain.repository.FavoriteRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteJpaRepository favoriteJpaRepository;

    public Favorite save(Favorite favorite) { return favoriteJpaRepository.save(favorite); }

    public Optional<Favorite> findByRestaurantAndUserId(UUID restaurantId, Long userId) {
        return favoriteJpaRepository.findByRestaurant_IdAndUserId(restaurantId,userId);
    }


}
