package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.*;
import com.waitless.restaurant.menu.application.mapper.MenuServiceMapper;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.domain.repository.MenuRepository;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuServiceMapper menuServiceMapper;

    @Transactional
    public CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto) {
        // TODO : 예외처리
        return menuServiceMapper.toResponseDto(menuRepository.save(menuServiceMapper.toMenu(createMenuDto)));
    }

    @Transactional(readOnly = true)
    // TODO : 예외처리
    public MenuDto getMenu(UUID id) {
        return menuServiceMapper.toMenuDto(getMenuFromRepo(id));
    }

    @Transactional
    public MenuDto deleteMenu(UUID id) {
        Menu menu = getMenuFromRepo(id);
        menu.delete();
        return menuServiceMapper.toMenuDto(menuRepository.save(menu));
    }

    @Transactional
    public UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto) {
        Menu oldMenu = getMenuFromRepo(id);
        Menu updateMenu = menuServiceMapper.toMenuFromUpdateMenu(updateMenuDto);
        return menuServiceMapper.toUpdateResponseDto(menuRepository.save(Menu.of(oldMenu, updateMenu)));
    }



    @Transactional(readOnly = true)
    public List<MenuDto> getMenus(UUID restaurantId) {
        return menuServiceMapper.toMenuDtoList(menuRepository.findAllByRestaurantId(restaurantId));
    }

    @Transactional
    public void deleteAllMenusByRestaurantId(UUID RestaurantId) {
        List<Menu> menuList = menuRepository.findAllByRestaurantId(RestaurantId);
        menuList.forEach(Menu::delete);
    }

    @Transactional(readOnly = true)
    public Page<SearchResponseDto> searchMenu(SearchMenuDto searchMenuDto, Pageable pageable) {
        Page<Menu> menuList = menuRepository.searchMenu(menuServiceMapper.toMenuDomain(searchMenuDto), pageable);
        System.out.println(menuList.getContent());

        return menuList.map(menuServiceMapper::toSearchResponse);
    }

    private Menu getMenuFromRepo(UUID id) {
        return menuRepository.getMenu(id).orElseThrow(() -> new NullPointerException("메뉴 id 없음"));
    }
}
