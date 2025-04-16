package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CategoryResponseDto;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    CategoryResponseDto createCategory(String name);

    List<CategoryResponseDto> getAllCategoryList();
}
