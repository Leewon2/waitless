package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.*;
import com.waitless.restaurant.menu.application.mapper.MenuServiceMapper;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

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
        return menuServiceMapper.toMenuDto(menuRepository.getMenu(id));
    }

    @Transactional
    public MenuDto deleteMenu(UUID id) {
        Menu menu = menuRepository.getMenu(id);
        menu.delete();
        return menuServiceMapper.toMenuDto(menuRepository.save(menu));
    }

    @Transactional
    public UpdatedMenuResponseDto updateMenu(UUID id, UpdateMenuDto updateMenuDto) {
        Menu oldMenu = menuRepository.getMenu(id);
        Menu updateMenu = menuServiceMapper.toMenuFromUpdateMenu(updateMenuDto);
        return menuServiceMapper.toUpdateResponseDto(menuRepository.save(Menu.of(oldMenu, updateMenu)));
    }
}
