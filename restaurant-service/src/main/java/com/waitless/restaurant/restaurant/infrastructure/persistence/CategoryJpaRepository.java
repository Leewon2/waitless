package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.waitless.restaurant.restaurant.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);
}
