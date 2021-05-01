package com.depromeet.muyaho.controller.advice;

import com.depromeet.muyaho.config.exception.ConflictException;
import com.depromeet.muyaho.config.exception.MuyahoException;
import com.depromeet.muyaho.config.exception.NotFoundException;
import com.depromeet.muyaho.config.exception.ValidationException;
import com.depromeet.muyaho.controller.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Object> handleValidationException(ValidationException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(exception.getErrorCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Object> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(exception.getErrorCode());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ApiResponse<Object> handleConflictException(ConflictException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(exception.getErrorCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MuyahoException.class)
    public ApiResponse<Object> handleMuyahoException(MuyahoException exception) {
        log.error(exception.getMessage());
        return ApiResponse.error(exception.getErrorCode());
    }

}
