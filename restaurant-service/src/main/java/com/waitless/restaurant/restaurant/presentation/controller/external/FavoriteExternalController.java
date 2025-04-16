package com.waitless.restaurant.restaurant.presentation.controller.external;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import com.waitless.restaurant.restaurant.application.service.FavoriteService;
import com.waitless.restaurant.restaurant.presentation.dto.FavoriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants/favorite")
@RequiredArgsConstructor
public class FavoriteExternalController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteRequestDto requestDto, @RequestHeader("X-User-Id") Long userId) {
        FavoriteResponseDto resultDto = favoriteService.addFavorite(requestDto.restaurantId(), userId);
        return ResponseEntity.ok(resultDto);
    }


}
