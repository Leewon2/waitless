package com.waitless.restaurant.restaurant.presentation.controller;

import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.restaurant.restaurant.application.dto.CategoryResponseDto;
import com.waitless.restaurant.restaurant.application.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody String name) {
        CategoryResponseDto responseDto = categoryService.createCategory(name);
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllCategoryList() {
        return ResponseEntity.ok(MultiResponse.success(categoryService.getAllCategoryList()));
    }
}
