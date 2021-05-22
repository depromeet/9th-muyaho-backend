package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Money {

    @Column(nullable = false)
    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    private Money(double money, CurrencyType currencyType) {
        validateValidMoney(money);
        this.money = new BigDecimal(money);
        this.currencyType = currencyType;
    }

    private void validateValidMoney(double money) {
        if (money < 0) {
            throw new ValidationException(String.format("Money(%s) 은 0보다 커야합니다", money));
        }
    }

    public static Money of(double money, CurrencyType currencyType) {
        return new Money(money, currencyType);
    }

}
