package com.waitless.restaurant.menu.domain.repository;

import com.waitless.restaurant.menu.domain.entity.Menu;

public interface MenuRepository {
    Menu save(Menu menu);
}
