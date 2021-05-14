package com.depromeet.muyaho.domain.common;

import com.depromeet.muyaho.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money {

    @Column(nullable = false)
    private BigDecimal money;

    private Money(double money) {
        validateValidMoney(money);
        this.money = new BigDecimal(money);
    }

    private void validateValidMoney(double money) {
        if (money < 0) {
            throw new ValidationException(String.format("Money(%s) 은 0보다 커야합니다", money));
        }
    }

    public static Money of(double money) {
        return new Money(money);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Objects.equals(money, money1.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }

}
