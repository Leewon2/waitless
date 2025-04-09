package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.repository.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository CategoryJpaRepository;

    public Optional<Category> findById(Long id) {return CategoryJpaRepository.findById(id);}

}
