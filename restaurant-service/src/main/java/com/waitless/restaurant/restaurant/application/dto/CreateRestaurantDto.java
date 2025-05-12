package com.waitless.restaurant.restaurant.application.dto;

import java.time.LocalTime;

public record CreateRestaurantDto(String name, Long ownerId, Long categoryId, String phone, Double latitude,
                                  Double longitude,
                                  Integer maxTableCount,
                                  LocalTime openingTime,
                                  LocalTime closingTime

) { }
