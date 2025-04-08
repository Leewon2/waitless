package com.waitless.restaurant.menu.application.mapper;

import com.waitless.restaurant.menu.application.dto.CreateMenuDto;
import com.waitless.restaurant.menu.application.dto.CreatedMenuResponseDto;
import com.waitless.restaurant.menu.domain.entity.Menu;
import com.waitless.restaurant.menu.presentation.dto.CreateMenuRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {
    @Mapping(target = "category", expression = "java(MenuCategory.from(createMenuRequestDto.category()))")
    CreateMenuDto toServiceDto(CreateMenuRequestDto createMenuRequestDto);

    CreatedMenuResponseDto toResponseDto(Menu menu);
}
