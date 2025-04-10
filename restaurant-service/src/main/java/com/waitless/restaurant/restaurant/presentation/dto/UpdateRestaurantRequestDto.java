package com.waitless.restaurant.restaurant.presentation.dto;

import java.time.LocalTime;

public record UpdateRestaurantRequestDto(String phone, LocalTime openingTime, LocalTime closingTime) {

}
