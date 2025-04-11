package com.waitless.common.exception;

import com.waitless.common.exception.code.CommonErrorCode;
import com.waitless.common.exception.code.ErrorCode;
import com.waitless.common.exception.response.SingleResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.badRequest().body(SingleResponse.errorWithData(errorCode.getMessage(), errorCode.getCode(), e.getData()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(SingleResponse.error(message, CommonErrorCode.VALIDATION_ERROR.getCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body( SingleResponse.error(message, CommonErrorCode.CONSTRAINT_VIOLATION.getCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("Method Not Supported: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(SingleResponse.error(CommonErrorCode.METHOD_NOT_ALLOWED.getMessage(), CommonErrorCode.METHOD_NOT_ALLOWED.getCode()));}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(SingleResponse.error(e.getMessage(), CommonErrorCode.INVALID_REQUEST.getCode()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.internalServerError().body(SingleResponse.error(e.getMessage(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return ResponseEntity.internalServerError().body(SingleResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage(), CommonErrorCode.INTERNAL_SERVER_ERROR.getCode()));
    }
}
