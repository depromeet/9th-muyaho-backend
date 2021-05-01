package com.depromeet.muyaho.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    UNAUTHORIZED_EXCEPTION("C001", "세션이 만료되었습니다"),
    NOT_FOUND_EXCEPTION("C002", "존재하지 않습니다"),
    VALIDATION_EXCEPTION("C003", "잘못된 입력입니다"),
    CONFLICT_EXCEPTION("C004", "이미 존재합니다"),
    INTERNAL_SERVER_EXCEPTION("C005", "서버 내부에서 에러가 발생하였습니다"),

    // Member
    MEMBER_EMAIL_FORMAT_EXCEPTION("M001", "이메일 포맷에 어긋납니다");

    private final String code;
    private final String message;

}
