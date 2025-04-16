package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.application.dto.CategoryResponseDto;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import com.waitless.restaurant.restaurant.application.exception.RestaurantErrorCode;
import com.waitless.restaurant.restaurant.application.mapper.CategoryServiceMapper;
import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryServiceMapper categoryServiceMapper;

    @Transactional
    public CategoryResponseDto createCategory(String name) {
        if(categoryRepository.existsByName(name)) {
            throw new RestaurantBusinessException(RestaurantErrorCode.CATEGORY_DUPLICATE);
        }

        return  categoryServiceMapper.toResponseDto(categoryRepository.save(Category.of(name)));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategoryList() {
        return categoryServiceMapper.toResponseDto(categoryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
      return   categoryRepository.findById(id).orElseThrow(
            () -> RestaurantBusinessException.from(RestaurantErrorCode.MENU_NOT_FOUND));
    }

}
