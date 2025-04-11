package com.waitless.restaurant.restaurant.application.dto;

import com.waitless.restaurant.menu.application.dto.MenuDto;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RestaurantWithMenuResponseDto(UUID id,
                                            String name,
                                            Long ownerId,
                                            String phone,
                                            String categoryName,
                                            LocalTime openingTime,
                                            LocalTime closingTime,
                                            List<MenuDto> menuList) {

}
