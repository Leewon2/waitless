package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    boolean existsByName(String name);
}
