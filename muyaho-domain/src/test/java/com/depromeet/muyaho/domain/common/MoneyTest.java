package com.depromeet.muyaho.domain.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {

    @Test
    void Money_클래스는_돈을_나타내는_객체이며_소수도_가능하다() {
        // given
        double price = 1.5;

        // when
        Money money = Money.of(price);

        // then
        assertThat(money.getMoney()).isEqualByComparingTo(new BigDecimal(price));
    }

    @Test
    void 돈이_0_보다_작은경우_VALIDATION_EXCEPTION_이_발생한다() {
        // given
        double price = -1.5;

        // when & then
        assertThatThrownBy(() -> Money.of(price));
    }

}
