package com.waitless.common.exception.response;

import lombok.Getter;

/**
 * 사용 예) return ResponseEntity.ok(SingleResponse.success(paymentResponseDto));
 *     예) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SingleResponse.error(e.getMessage(), "ORDER_ERROR_2"));
 */
@Getter
public class SingleResponse<T> {
    private final T data;
    private final String errorMessage;
    private final String errorCode;

    //성공 응답
    public SingleResponse(T data) {
        this.data = data;
        this.errorMessage = null;
        this.errorCode = null;
    }

    //에러응답
    public SingleResponse(String errorMessage, String errorCode) {
        this.data = null;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    // 에러 응답 + 데이터 포함
    public SingleResponse(T data, String errorMessage, String errorCode) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static <T> SingleResponse<T> success(T data) {
        return new SingleResponse<>(data);
    }

    public static <T> SingleResponse<T> error(String errorMessage, String errorCode) {
        return new SingleResponse<>(errorMessage, errorCode);
    }

    public static <T> SingleResponse<T> errorWithData(String errorMessage, String errorCode, T data) {
        return new SingleResponse<>(data, errorMessage, errorCode);
    }
}
