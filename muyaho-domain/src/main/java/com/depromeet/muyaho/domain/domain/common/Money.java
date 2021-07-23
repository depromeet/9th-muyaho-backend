package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.common.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money {

    @Column(nullable = false)
    private BigDecimal money;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    private Money(BigDecimal money, CurrencyType currencyType) {
        validateValidMoney(money);
        this.money = money;
        this.currencyType = currencyType;
    }

    private void validateValidMoney(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(String.format("Money(%s) 은 0보다 커야합니다", money), ErrorCode.VALIDATION_INVALID_MONEY_EXCEPTION);
        }
    }

    public static Money of(BigDecimal money, CurrencyType currencyType) {
        return new Money(money, currencyType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Objects.equals(money.stripTrailingZeros(), money1.money.stripTrailingZeros()) && currencyType == money1.currencyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, currencyType);
    }

}
