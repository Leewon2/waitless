package com.waitless.restaurant.menu.application.service;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.mapper.MenuMapper;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public CreatedMenuResponseDto createMenu(CreateMenuDto createMenuDto) {
        // TODO : 예외처리
        return menuMapper.toResponseDto(menuRepository.save(Menu.of(createMenuDto)));
    }
}
