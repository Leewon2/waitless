package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import com.waitless.restaurant.restaurant.application.exception.RestaurantErrorCode;
import com.waitless.restaurant.restaurant.application.mapper.FavoriteServiceMapper;
import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.FavoriteRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RestaurantService restaurantService;
    private final FavoriteServiceMapper favoriteServiceMapper;

    @Transactional
    public FavoriteResponseDto addFavorite(UUID restaurantId, Long userId) {
        Favorite favorite;
        Optional<Favorite> favoriteOptional = favoriteRepository.findByRestaurantAndUserId(restaurantId, userId);

        if(favoriteOptional.isPresent()) {
            favorite = favoriteOptional.get();
            // 즐겨찾기가 이미 등록된 경우
            if(!favorite.isDeleted()) throw RestaurantBusinessException.from(RestaurantErrorCode.FAVORITE_DUPLICATE);
            // 삭제 취소
            favorite.revert();
        }else {
            Restaurant restaurant = restaurantService.findById(restaurantId);
            favorite = Favorite.of(restaurant, userId);
            favoriteRepository.save(favorite);
        }

        return favoriteServiceMapper.toResponseDto(favorite);
    }

    @Transactional
    public FavoriteResponseDto deleteFavorite(UUID id) {
        Favorite favorite = findFavoriteById(id);
        favorite.delete();

        return favoriteServiceMapper.toResponseDto(favorite);
    }

    @Transactional(readOnly = true)
    public Page<FavoriteResponseDto> getFavoriteList(Long userId, Pageable pageable) {
        Page<Favorite> favoritePage = favoriteRepository.findAllByUserId(userId, pageable);
        return favoriteServiceMapper.toResponseDtoPage(favoritePage);
    }

    @Transactional
    public Favorite findFavoriteById(UUID id) {
        return favoriteRepository.findById(id)
            .orElseThrow(()->RestaurantBusinessException.from(RestaurantErrorCode.FAVORITE_NOT_FOUND));
    }


}
