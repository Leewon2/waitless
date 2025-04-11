package com.waitless.restaurant.restaurant.application.mapper;

import com.waitless.restaurant.menu.application.dto.MenuDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantResponseDto;
import com.waitless.restaurant.restaurant.application.dto.RestaurantWithMenuResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantServiceMapper {

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "location.latitude", target = "latitude")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "operatingHours.openingTime", target = "openingTime")
    @Mapping(source = "operatingHours.closingTime", target = "closingTime")
    RestaurantResponseDto toResponseDto(Restaurant restaurant);

    @Mapping(source = "restaurant.category.name", target = "categoryName")
    @Mapping(source = "restaurant.operatingHours.openingTime", target = "openingTime")
    @Mapping(source = "restaurant.operatingHours.closingTime", target = "closingTime")
    RestaurantWithMenuResponseDto toWithMenuDto(Restaurant restaurant, List<MenuDto> menuList);

}
