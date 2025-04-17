package com.waitless.review.application.dto.command;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageCommand<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static <T> PageCommand<T> from(Page<T> page) {
        return new PageCommand<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}