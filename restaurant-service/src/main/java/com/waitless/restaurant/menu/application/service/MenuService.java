package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.MenuDto;

import java.util.UUID;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);

    MenuDto getMenu(UUID id);

    MenuDto deleteMenu(UUID id);
}
