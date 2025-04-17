package com.waitless.restaurant.restaurant.application.mapper;

import com.waitless.restaurant.restaurant.application.dto.FavoriteResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoriteServiceMapper {

    @Mapping(source = "restaurant.name", target = "restaurantName")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    FavoriteResponseDto toResponseDto(Favorite favorite);

}
