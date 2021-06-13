package com.depromeet.muyaho.common.exception;

public class BadGatewayException extends MuyahoException {

    public BadGatewayException(String message) {
        super(message, ErrorCode.BAD_GATEWAY_EXCEPTION);
    }

}
