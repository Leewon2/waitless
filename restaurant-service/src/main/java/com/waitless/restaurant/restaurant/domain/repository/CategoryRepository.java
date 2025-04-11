package com.waitless.restaurant.restaurant.domain.repository;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import java.util.Optional;

public interface CategoryRepository {

    Optional<Category> findById(Long id);
}
