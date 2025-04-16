package com.waitless.restaurant.restaurant.application.mapper;

import com.waitless.restaurant.restaurant.application.dto.CategoryResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryServiceMapper {

    CategoryResponseDto toResponseDto(Category category);

    List<CategoryResponseDto> toResponseDto(List<Category> categoryList);

}
