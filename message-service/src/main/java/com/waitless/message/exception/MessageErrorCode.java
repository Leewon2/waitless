package com.waitless.message.exception;

import com.waitless.common.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public enum MessageErrorCode implements ErrorCode {


    MESSAGE_NOT_FOUND("MSG_001", "존재하는 슬래 메세지가 없니다."),
    REVIEW_REQUEST_KAFKA_ERROR("MSG_002", "리뷰 요청 카프카 수신 오류");
    private final String code;
    private final String message;
    MessageErrorCode(String code, String message){
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
