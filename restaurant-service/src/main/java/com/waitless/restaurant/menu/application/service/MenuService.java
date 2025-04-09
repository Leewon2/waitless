package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.*;

import java.util.UUID;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);

    MenuDto getMenu(UUID id);

    MenuDto deleteMenu(UUID id);

    UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto);
}
