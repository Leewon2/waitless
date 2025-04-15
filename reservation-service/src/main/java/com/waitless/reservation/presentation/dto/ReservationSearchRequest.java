package com.waitless.reservation.presentation.dto;

import com.waitless.reservation.domain.entity.ReservationStatus;

public record ReservationSearchRequest(
        ReservationStatus status,
        Long userId,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {
    public ReservationSearchRequest {
        status = defaultStatus(status);
        userId = defaultUserId(userId);
        page = defaultPage(page);
        size = defaultSize(size);
        sortBy = defaultSortBy(sortBy);
        direction = defaultDirection(direction);
    }

    private static ReservationStatus defaultStatus(ReservationStatus status) {
        return status != null ? status : ReservationStatus.WAITING;
    }

    private static Long defaultUserId(Long userId) {
        return userId != null ? userId : 1L; // 추후 변경 예정
    }

    private static int defaultPage(Integer page) {
        return (page != null && page > 0) ? page : 1;
    }

    private static int defaultSize(Integer size) {
        return (size != null && size > 0) ? size : 10;
    }

    private static String defaultSortBy(String sortBy) {
        return (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
    }

    private static String defaultDirection(String direction) {
        return (direction == null || direction.isBlank()) ? "desc" : direction.toLowerCase();
    }
}