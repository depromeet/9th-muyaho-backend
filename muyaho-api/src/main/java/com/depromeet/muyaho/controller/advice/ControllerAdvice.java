package com.depromeet.muyaho.controller.advice;

import com.depromeet.muyaho.config.exception.*;
import com.depromeet.muyaho.controller.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ApiResponse<Object> handleUnAuthorizedException(final UnAuthorizedException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.UNAUTHORIZED_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Object> handleValidationException(final ValidationException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.VALIDATION_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Object> handleNotFoundException(final NotFoundException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.NOT_FOUND_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ApiResponse<Object> handleConflictException(final ConflictException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.CONFLICT_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MuyahoException.class)
    public ApiResponse<Object> handleMuyahoException(final MuyahoException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

}
