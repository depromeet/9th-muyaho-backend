package com.depromeet.muyaho.common.exception;

public class ConflictException extends MuyahoException {

    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }

    public ConflictException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
