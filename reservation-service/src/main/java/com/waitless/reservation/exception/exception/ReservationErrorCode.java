package com.waitless.reservation.exception.exception;

import com.waitless.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public enum ReservationErrorCode implements ErrorCode {

    RESERVATION_STOCK_ERROR("RES_001", "재고가 부족합니다."),
    RESERVATION_MENU_NOTFOUND_ERROR("RES_002", "존재하지 않는 메뉴입니다."),
    RESERVATION_RESTAURANT_NOT_FOUND("RES_003", "존재하지 않는 식당입니다."),
    UNKNOWN_LUA_RESULT("RES_999", "알 수 없는 Redis Lua 실행 결과입니다."),
    RESERVATION_TEAM_LIMIT_EXCEEDED("RES_004", "웨이팅이 마감되었습니다"),
    RESERVATION_NOT_FOUND("RES_005", "예약을 찾을 수 없습니다");
    private final String code;
    private final String message;

    ReservationErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
