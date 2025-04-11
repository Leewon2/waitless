package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.*;
import com.waitless.restaurant.menu.domain.entity.Menu;

import java.util.List;
import java.util.UUID;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);

    MenuDto getMenu(UUID id);

    MenuDto deleteMenu(UUID id);

    UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto);

    List<MenuDto> getMenus(UUID id);
}
