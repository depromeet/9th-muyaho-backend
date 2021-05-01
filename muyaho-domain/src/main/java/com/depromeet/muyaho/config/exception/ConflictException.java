package com.depromeet.muyaho.config.exception;

public class ConflictException extends MuyahoException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
