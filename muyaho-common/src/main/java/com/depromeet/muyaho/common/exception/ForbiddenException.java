package com.depromeet.muyaho.common.exception;

public class ForbiddenException extends MuyahoException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
