package com.waitless.restaurant.menu.application.service;

import com.waitless.common.dto.StockDto;
import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.dto.MenuDto;
import com.waitless.restaurant.menu.application.dto.SearchMenuDto;
import com.waitless.restaurant.menu.application.dto.SearchResponseDto;
import com.waitless.restaurant.menu.application.dto.UpdateMenuDto;
import com.waitless.restaurant.menu.application.dto.UpdatedMenuResponseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {
    CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto);

    MenuDto getMenu(UUID id);

    MenuDto deleteMenu(UUID id);

    UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto);

    Page<SearchResponseDto> searchMenu(SearchMenuDto searchMenuDto, Pageable pageable);

    List<MenuDto> getMenus(UUID id);

    void deleteAllMenusByRestaurantId(UUID RestaurantId);

    void decreaseMenuAmount(List<StockDto> stockList);
}
