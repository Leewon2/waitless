package com.waitless.restaurant.menu.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum MenuCategory {
    KOREAN, CHINESE, WESTERN, JAPANESE, OTHER;
    public static MenuCategory from(String name) {
        if(name==null) return null;
        return Arrays.stream(MenuCategory.values())
                .filter(category -> category.name().equalsIgnoreCase(name))
                .findFirst()
                // TODO : custom exception으로 변경
                .orElseThrow(() -> new IllegalArgumentException("카테고리 불일치"));
    }
}
