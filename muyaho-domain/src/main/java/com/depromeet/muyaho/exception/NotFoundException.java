package com.depromeet.muyaho.exception;

public class NotFoundException extends MuyahoException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
