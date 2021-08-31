package com.depromeet.muyaho.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
    UNSUPPORTED_MEDIA_TYPE("C009", "허용하지 않는 미디어 타입입니다"),

    // Validation Exception
    VALIDATION_INVALID_TOKEN_EXCEPTION("V002", "잘못된 토큰입니다"),
    VALIDATION_INVALID_EMAIL_EXCEPTION("V003", "이메일 포맷에 어긋납니다"),
    VALIDATION_INVALID_MONEY_EXCEPTION("V004", "잘못된 금액을 입력하였습니다"),
    VALIDATION_INVALID_QUANTITY_EXCEPTION("V005", "잘못된 보유 수량을 입력하였습니다"),
    VALIDATION_ESSENTIAL_TOTAL_PURCHASE_PRICE_EXCEPTION("V006", "해당하는 주식은 매입금을 필수로 입력해야합니다"),

    // Forbidden Exception
    FORBIDDEN_STOCK_CURRENCY_EXCEPTION("F001", "해당하는 주식이 허용하지 않는 화폐 통화입니다"),

    // NotFound Exception
    NOT_FOUND_STOCK_EXCEPTION("N001", "해당하는 주식은 존재하지 않습니다"),
    NOT_FOUND_MEMBER_EXCEPTION("N002", "존재하지 않는 회원입니다"),

    // Conflict Exception
    CONFLICT_MEMBER_STOCK_EXCEPTION("C001", "이미 소유하고 있는 주식입니다"),
    CONFLICT_NICKNAME_EXCEPTION("C002", "이미 존재하는 닉네임입니다"),
    CONFLICT_MEMBER_EXCEPTION("C003", "이미 존재하는 회원입니다");

    private final String code;
    private final String message;

}
