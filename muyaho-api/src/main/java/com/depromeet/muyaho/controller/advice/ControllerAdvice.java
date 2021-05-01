package com.depromeet.muyaho.controller.advice;

import com.depromeet.muyaho.config.exception.*;
import com.depromeet.muyaho.controller.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Spring Validation Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String field = e.getBindingResult().getFieldError() == null ? "" : e.getBindingResult().getFieldError().getField();
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION, String.format("%s이 %s", field, e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION);
    }

    /**
     * enum 타입이 일치하지 않을 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponse<Object> handleMethodArgumentTypeMismatchException(InvalidFormatException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(ErrorCode.METHOD_NOT_ALLOWED_EXCEPTION);
    }

    /**
     * 세션에 문제가 있는 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    protected ApiResponse<Object> handleUnAuthorizedException(final UnAuthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.UNAUTHORIZED_EXCEPTION);
    }

    /**
     * 잘못된 입력이 들어왔을 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Object> handleValidationException(final ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION);
    }

    /**
     * 존재하지 않는 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<Object> handleNotFoundException(final NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.NOT_FOUND_EXCEPTION);
    }

    /**
     * 이미 존재하는 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    protected ApiResponse<Object> handleConflictException(final ConflictException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.CONFLICT_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MuyahoException.class)
    protected ApiResponse<Object> handleMuyahoException(final MuyahoException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponse<Object> handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

}
