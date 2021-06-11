package com.depromeet.muyaho.common.exception;

public class ValidationException extends MuyahoException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
