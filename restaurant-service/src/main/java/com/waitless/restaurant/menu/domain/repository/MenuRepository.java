package com.waitless.restaurant.menu.domain.repository;

import com.waitless.restaurant.menu.domain.dto.MenuSearchDomainDto;
import com.waitless.restaurant.menu.domain.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> getMenu(UUID id);

    List<Menu> findAllByRestaurantId(UUID restaurantId);

    Page<Menu> searchMenu(MenuSearchDomainDto menuSearchDomainDto, Pageable pageable);
}
