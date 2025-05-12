package com.waitless.restaurant.restaurant.presentation.mapper;

import com.waitless.restaurant.restaurant.application.dto.CreateRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.SearchRestaurantDto;
import com.waitless.restaurant.restaurant.application.dto.UpdateRestaurantDto;
import com.waitless.restaurant.restaurant.presentation.dto.CreateRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.SearchRestaurantRequestDto;
import com.waitless.restaurant.restaurant.presentation.dto.UpdateRestaurantRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantControllerMapper {

    CreateRestaurantDto toServiceDto(CreateRestaurantRequestDto requestDto);

    UpdateRestaurantDto toServiceDto(UpdateRestaurantRequestDto requestDto);

    SearchRestaurantDto toServiceDto(SearchRestaurantRequestDto requestDto);
}
