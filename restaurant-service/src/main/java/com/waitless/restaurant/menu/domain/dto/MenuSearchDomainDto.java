package com.waitless.restaurant.menu.domain.dto;

import com.waitless.restaurant.menu.domain.entity.enums.MenuCategory;

import java.util.Optional;

public record MenuSearchDomainDto(Integer minPrice,
                                  Integer maxPrice,
                                  MenuCategory category,
                                  String sortBy,
                                  Integer page,
                                  Integer size) {
}
