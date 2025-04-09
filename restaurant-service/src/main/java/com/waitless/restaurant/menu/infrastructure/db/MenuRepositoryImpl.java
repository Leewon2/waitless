package com.waitless.restaurant.menu.infrastructure.db;

import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.application.mapper.MenuMapper;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuJpaRepository menuJpaRepository;

    public Menu save(Menu menu) {
        return menuJpaRepository.save(menu);
    }
}
