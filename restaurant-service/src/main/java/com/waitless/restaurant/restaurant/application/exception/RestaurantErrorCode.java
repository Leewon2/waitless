package com.waitless.restaurant.restaurant.application.exception;

import com.waitless.common.exception.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RestaurantErrorCode implements ErrorCode {

    RESTAURANT_NOT_FOUND("RESTAURANT_1", "해당 식당을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RESTAURANT_ALREADY_CLOSED("RESTAURANT_2", "이미 영업이 종료된 식당입니다.", HttpStatus.BAD_REQUEST),

    MENU_NOT_FOUND("MENU_1","해당 메뉴를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MENU_DECREASE_FAILED("MENU_2", "메뉴 재고를 감소할 수 없습니다.", HttpStatus.BAD_REQUEST),

    CATEGORY_NOT_FOUND("CATEGORY_1","해당 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CATEGORY_DUPLICATE("CATEGORY_2","카테고리 이름은 중복될 수 없습니다.", HttpStatus.BAD_REQUEST),

    FAVORITE_NOT_FOUND("FAVORITE_1", "해당 식당은 이미 즐겨찾기가 되어있습니다.", HttpStatus.NOT_FOUND),
    FAVORITE_DUPLICATE("FAVORITE_2", "해당 식당은 이미 즐겨찾기가 되어있습니다.", HttpStatus.BAD_REQUEST);


    private final String code;
    private final String message;
    private final int status;

    RestaurantErrorCode(final String code, final String message, final HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status.value();
    }
}
