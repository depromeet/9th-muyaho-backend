package com.depromeet.muyaho.domain.member;

import com.depromeet.muyaho.config.exception.ErrorCode;
import com.depromeet.muyaho.config.exception.ValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Email {

    private final static Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.,-]+\\.[a-zA-Z]{2,6}$");

    @Column
    private String email;

    private Email(String email) {
        validateFormat(email);
        this.email = email;
    }

    private void validateFormat(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new ValidationException(String.format("(%s)은 이메일 포맷에 맞지 않습니다", email), ErrorCode.MEMBER_EMAIL_FORMAT_EXCEPTION);
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }

}
