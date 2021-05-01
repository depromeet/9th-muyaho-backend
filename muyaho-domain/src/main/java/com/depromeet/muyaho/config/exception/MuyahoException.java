package com.depromeet.muyaho.config.exception;

import lombok.Getter;

@Getter
public abstract class MuyahoException extends RuntimeException {

    private ErrorCode errorCode;

    public MuyahoException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
