package com.waitless.restaurant.restaurant.application.dto;

import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponseDto(UUID id,
                                    String name,
                                    Long ownerId,
                                    String phone,
                                    String categoryName,
                                    Double latitude,
                                    int maxTableCount,
                                    Double longitude,
                                    LocalTime openingTime,
                                    LocalTime closingTime,
                                    boolean isOpened
) {

}
