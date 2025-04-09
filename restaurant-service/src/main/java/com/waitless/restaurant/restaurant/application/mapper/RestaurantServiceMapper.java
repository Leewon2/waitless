package com.waitless.restaurant.restaurant.application.mapper;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
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
    CreateRestaurantResponseDto toResponseDto(Restaurant restaurant);
}
