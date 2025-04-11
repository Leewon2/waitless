package com.waitless.restaurant.restaurant.application.service;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
      return   categoryRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("카테고리를 찾을 수 없습니다.")
        );
    }
}
