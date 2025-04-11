package com.waitless.restaurant.restaurant.application.dto;

import java.time.LocalTime;

public record UpdateRestaurantDto(String phone, LocalTime openingTime, LocalTime closingTime) {
}
