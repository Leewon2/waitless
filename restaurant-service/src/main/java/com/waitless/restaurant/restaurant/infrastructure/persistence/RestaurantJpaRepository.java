package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, UUID> {

    List<Restaurant> findByIdIn(Collection<UUID> ids);
}
