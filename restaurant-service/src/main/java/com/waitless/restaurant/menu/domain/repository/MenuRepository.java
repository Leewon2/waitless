package com.waitless.restaurant.menu.domain.repository;

import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;

public interface MenuRepository {
    Menu save(Menu menu);
}
