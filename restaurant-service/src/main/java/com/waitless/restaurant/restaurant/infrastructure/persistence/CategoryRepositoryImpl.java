package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import com.waitless.restaurant.restaurant.domain.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public Category save(Category category) {return categoryJpaRepository.save(category);}

    public Optional<Category> findById(Long id) {return categoryJpaRepository.findById(id);}

    @Override
    public List<Category> findAll() { return categoryJpaRepository.findAll();}

    public boolean existsByName(String name) {return categoryJpaRepository.existsByName(name);}

}
