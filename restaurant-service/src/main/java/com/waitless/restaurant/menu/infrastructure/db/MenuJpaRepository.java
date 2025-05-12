package com.waitless.restaurant.menu.infrastructure.db;

import com.waitless.restaurant.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuJpaRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findAllByRestaurantId(UUID restaurantId);
}
