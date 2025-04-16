package com.waitless.restaurant.restaurant.presentation.controller.external;

import com.waitless.common.exception.response.MultiResponse;
import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import com.waitless.restaurant.restaurant.application.service.FavoriteService;
import com.waitless.restaurant.restaurant.presentation.dto.FavoriteRequestDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeFavorite(@PathVariable("id") UUID id) {
        FavoriteResponseDto responseDto = favoriteService.deleteFavorite(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getFavoriteList(Pageable pageable,@RequestHeader("X-User-Id") Long userId) {
        Page<FavoriteResponseDto> responsePage = favoriteService.getFavoriteList(userId,pageable);

        return ResponseEntity.ok(MultiResponse.success(responsePage));
    }


}
