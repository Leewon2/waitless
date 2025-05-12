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
    RESERVATION_NOT_FOUND("RES_005", "예약을 찾을 수 없습니다"),
    RESTAURANT_BAD_REQUEST("RES_006", "요청 데이터 오류"),
    RESTAURANT_SERVER_ERROR("RES_007", "레스토랑 서버에 문제가 발생하였습니다."),
    RESERVATION_STOCK_RESTORE_ERROR("RES_008", "존재하지 않는 메뉴입니다"),
    RESTAURANT_OWNER_UNAUTHORIZED("RES_009", "식당 소유주만 해당 작업을 수행할 수 있습니다."),
    USER_NOT_FOUND("RES_010", "존재하지 않는 유저입니다"),
    USER_SERVER_ERROR("RES_011", "유저 서버에 문제가 발생하였습니다"),
    RESERVATION_STATUS_ERROR("RES_012", "예약 상태가 웨이팅중이 아닙니다."),
    RESERVATION_UNAUTHORIZED("RES_013", "검증 도중 문제가 발생하였습니다."),
    RESERVATION_TICKETING_RESTAURANT_NOT_FOUND("REST_014", "티켓팅 식당이 존재하지 않습니다"),
    RESERVATION_DELAY_FAIL("RES_015", "순번 미루기 횟수가 남아있지 않습니다");
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
