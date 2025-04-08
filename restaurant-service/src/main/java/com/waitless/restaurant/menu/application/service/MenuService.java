package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);
}
