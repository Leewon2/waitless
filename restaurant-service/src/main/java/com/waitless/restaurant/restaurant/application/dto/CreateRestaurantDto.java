package com.waitless.restaurant.restaurant.application.dto;

import com.waitless.restaurant.restaurant.domain.vo.Location;
import com.waitless.restaurant.restaurant.domain.vo.OperatingHours;
import java.time.LocalTime;

public record CreateRestaurantDto(String name, Long ownerId, Long categoryId, String phone, Double latitude, Double longitude,
                                  LocalTime openingTime,
                                  LocalTime closingTime

) {
    public Location toLocation() {
        return Location.builder()
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }

    public OperatingHours toOperatingHours() {
        return OperatingHours.builder()
            .closingTime(closingTime)
            .openingTime(openingTime)
            .build();
    }
}
