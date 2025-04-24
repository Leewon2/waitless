package com.waitless.reservation.presentation.dto;

import com.waitless.reservation.domain.entity.ReservationStatus;

public record ReservationSearchRequest(
        ReservationStatus status,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {
    public ReservationSearchRequest {
        status = defaultStatus(status);
        page = defaultPage(page);
        size = defaultSize(size);
        sortBy = defaultSortBy(sortBy);
        direction = defaultDirection(direction);
    }

    private static ReservationStatus defaultStatus(ReservationStatus status) {
        return status != null ? status : ReservationStatus.WAITING;
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