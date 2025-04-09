package com.waitless.common.exception.code;

import lombok.Getter;

@Getter
public enum CommonErrorCode implements ErrorCode {

    INVALID_REQUEST("COM_001", "잘못된 요청입니다."),
    UNAUTHORIZED("COM_002", "인증되지 않은 요청입니다."),
    FORBIDDEN("COM_003", "접근이 금지 되었습니다."),
    NOT_FOUND("COM_004", "요청한 데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("COM_005", "서버 에러 입니다."),
    NOT_IMPLEMENTED("COM_006", "요청한 URI 페이지는 없습니다."),
    BAD_GATEWAY("COM_007", "서버 간 통신이 올바르지 않습니다."),
    VALIDATION_ERROR("COM_008", "입력값 검증에 실패했습니다."),
    CONSTRAINT_VIOLATION("COM_009", "제약조건 위반이 발생했습니다."),
    METHOD_NOT_ALLOWED("COM_010", "지원하지 않는 HTTP 메서드입니다.");

    private final String code;
    private final String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
