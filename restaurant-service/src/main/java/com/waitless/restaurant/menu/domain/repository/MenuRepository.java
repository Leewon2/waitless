package com.waitless.restaurant.menu.domain.repository;

import com.waitless.restaurant.menu.domain.entity.Menu;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Menu getMenu(UUID id);

}
