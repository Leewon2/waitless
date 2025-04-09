package com.waitless.restaurant.menu.presentation.mapper;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuControllerMapper {
    @Mapping(target = "category", expression = "java(MenuCategory.from(createMenuRequestDto.category()))")
    CreateMenuDto toServiceDto(CreateMenuRequestDto createMenuRequestDto);
}
