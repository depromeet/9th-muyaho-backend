package com.depromeet.muyaho.domain.domain.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void Money_클래스는_돈을_나타내는_객체이며_소수도_가능하다() {
        // given
        BigDecimal price = new BigDecimal("1.5");

        // when
        Money money = Money.of(price, CurrencyType.DOLLAR);

        // then
        assertThat(money.getMoney()).isEqualByComparingTo(price);
    }

    @Test
    void 돈이_0_보다_작은경우_VALIDATION_EXCEPTION_이_발생한다() {
        // given
        BigDecimal price = new BigDecimal("-1.5");

        // when & then
        assertThatThrownBy(() -> Money.of(price, CurrencyType.DOLLAR));
    }

    @Test
    void 동등성_테스트_같은_통화_같은_금액일경우_같은_값이다() {
        // given
        BigDecimal price = new BigDecimal("1.5");
        CurrencyType type = CurrencyType.WON;

        Money target = Money.of(price, type);

        // when
        boolean result = target.equals(Money.of(price, type));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 동등성_테스트_같은_금액이더라더_통화가_다르면_다른_값이다() {
        // given
        BigDecimal price = new BigDecimal("1.5");
        Money target = Money.of(price, CurrencyType.DOLLAR);

        // when
        boolean result = target.equals(Money.of(price, CurrencyType.WON));

        // then
        assertThat(result).isFalse();
    }

}
