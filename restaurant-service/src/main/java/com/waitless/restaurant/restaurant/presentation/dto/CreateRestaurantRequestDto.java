package com.waitless.restaurant.restaurant.presentation.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalTime;

public record CreateRestaurantRequestDto(
    @NotBlank(message = "식당 명을 입력해주세요.")
    String name,

    @NotNull(message = "식당 주인을 선택해주세요.")
    Long ownerId,

    @NotNull(message = "식당 카테고리를 선택해주세요.")
    Long categoryId,

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
        message = "핸드폰 번호의 양식과 맞지 않습니다. xxx-xxx(x)-xxxx")
    String phone,

    @Min(0)
    Integer maxTableCount,

    @NotNull(message = "위도를 입력해주세요.")
    @DecimalMin(value = "-90.0", inclusive = true, message = "위도는 -90 이상이어야 합니다.")
    @DecimalMax(value = "90.0", inclusive = true, message = "위도는 90 이하여야 합니다.")
    Double latitude,

    @NotNull(message = "경도를 입력해주세요.")
    @DecimalMin(value = "-180.0", inclusive = true, message = "경도는 -180 이상이어야 합니다.")
    @DecimalMax(value = "180.0", inclusive = true, message = "경도는 180 이하여야 합니다.")
    Double longitude,

    @NotNull(message = "오픈 시간을 입력해주세요.")
    LocalTime openingTime,

    @NotNull(message = "마감 시간을 입력해주세요.")
    LocalTime closingTime
) {

}
