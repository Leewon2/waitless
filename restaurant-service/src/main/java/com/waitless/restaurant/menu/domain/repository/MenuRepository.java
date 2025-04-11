package com.waitless.restaurant.menu.domain.repository;

import com.waitless.restaurant.menu.domain.entity.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> getMenu(UUID id);

    List<Menu> findAllByRestaurantId(UUID restaurantId);
}
