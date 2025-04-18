package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import java.util.UUID;

public interface FavoriteService {

    FavoriteResponseDto addFavorite(UUID uuid, Long userId);
}
