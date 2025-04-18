package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {

    FavoriteResponseDto addFavorite(UUID uuid, Long userId);

    FavoriteResponseDto deleteFavorite(UUID id);

    Page<FavoriteResponseDto> getFavoriteList(Long userId, Pageable pageable);
}
