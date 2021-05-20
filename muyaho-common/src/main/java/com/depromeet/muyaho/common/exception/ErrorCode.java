package com.depromeet.muyaho.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    UNAUTHORIZED_EXCEPTION("C001", "세션이 만료되었습니다. 다시 로그인 해주세요"),
    NOT_FOUND_EXCEPTION("C002", "존재하지 않습니다"),
    VALIDATION_EXCEPTION("C003", "잘못된 요청입니다"),
    CONFLICT_EXCEPTION("C004", "이미 존재합니다"),
    INTERNAL_SERVER_EXCEPTION("C005", "서버 내부에서 에러가 발생하였습니다"),
    METHOD_NOT_ALLOWED_EXCEPTION("C006", "지원하지 않는 메소드 입니다"),
    BAD_GATEWAY_EXCEPTION("C007", "외부 연동 중 에러가 발생하였습니다"),
    FORBIDDEN_EXCEPTION("C008", "허용하지 않는 접근입니다."),

    VALIDATION_NOT_SUPPORTED_PROVIDER_EXCEPTION("V001", "아직 지원하고 있지 않은 소셜 타입입니다"),
    VALIDATION_INVALID_TOKEN_EXCEPTION("V002", "잘못된 토큰입니다"),
    VALIDATION_INVALID_EMAIL_EXCEPTION("V003", "이메일 포맷에 어긋납니다"),
    VALIDATION_INVALID_MONEY_EXCEPTION("V004", "잘못된 돈을 입력하셨습니다"),
    VALIDATION_INVALID_QUANTITY_EXCEPTION("V005", "잘못된 보유 수량입니다"),

    FORBIDDEN_STOCK_CURRENCY_EXCEPTION("F001", "해당하는 주식이 허용하지 않는 화폐 통화입니다");

    private final String code;
    private final String message;

}
