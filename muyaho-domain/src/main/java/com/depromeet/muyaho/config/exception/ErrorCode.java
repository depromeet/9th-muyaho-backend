package com.depromeet.muyaho.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Member
    MEMBER_NOT_FOUND_EXCEPTION(404, "M001", "존재하지 않는 회원입니다"),
    MEMBER_CONFLICT_EXCEPTION(409, "M002", "이미 존재하는 회원입니다"),
    MEMBER_NOT_EXIST_PROVIDER(400, "M003", "존재하지 않는 소셜 타입입니다");

    private final int status;
    private final String code;
    private final String message;

}
