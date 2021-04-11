package com.depromeet.muyaho.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Column(nullable = false)
    private String email;

    private Email(String email) {
        validateEmailPattern(email);
        this.email = email;
    }

    private void validateEmailPattern(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException(String.format("(%s)은 이메일 형식이 아닙니다", email));
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }

}
