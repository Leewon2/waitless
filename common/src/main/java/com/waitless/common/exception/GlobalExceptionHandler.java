package com.waitless.common.exception;

import com.waitless.common.exception.code.CommonErrorCode;
import com.waitless.common.exception.code.ErrorCode;
import com.waitless.common.exception.response.SingleResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public SingleResponse<Void> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return SingleResponse.error(errorCode.getMessage(), errorCode.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SingleResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return SingleResponse.error(message, CommonErrorCode.VALIDATION_ERROR.getCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public SingleResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        return SingleResponse.error(message, CommonErrorCode.CONSTRAINT_VIOLATION.getCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public SingleResponse<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("Method Not Supported: {}", e.getMessage());
        return SingleResponse.error(CommonErrorCode.METHOD_NOT_ALLOWED.getMessage(), CommonErrorCode.METHOD_NOT_ALLOWED.getCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public SingleResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return SingleResponse.error(e.getMessage(), CommonErrorCode.INVALID_REQUEST.getCode());
    }

    @ExceptionHandler(IllegalStateException.class)
    public SingleResponse<Void> handleIllegalStateException(IllegalStateException e) {
        return SingleResponse.error(e.getMessage(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode());
    }

    @ExceptionHandler(Exception.class)
    public SingleResponse<Void> handleGenericException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return SingleResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode());
    }
}
