package com.depromeet.muyaho.common.exception;

public class ForbiddenException extends MuyahoException {

    public ForbiddenException(String message) {
        super(message, ErrorCode.FORBIDDEN_EXCEPTION);
    }

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
