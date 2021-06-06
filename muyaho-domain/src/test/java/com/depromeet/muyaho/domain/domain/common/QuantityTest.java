package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuantityTest {

    @Test
    void Quantity_는_보유수량을_나타내는_객체이며_소수도_가능하다() {
        // given
        BigDecimal count = new BigDecimal(3);

        // when
        Quantity quantity = Quantity.of(count);

        // then
        assertThat(quantity.getQuantity()).isEqualByComparingTo(count);
    }

    @Test
    void 보유수량이_0일경우_통과한다() {
        // given
        BigDecimal count = new BigDecimal(0);

        // when
        Quantity quantity = Quantity.of(count);

        // then
        assertThat(quantity.getQuantity()).isEqualByComparingTo(count);
    }

    @Test
    void 보유수량이_0이하일경우_VALIDATION_EXCEPTION이_발생한다() {
        // given
        BigDecimal count = new BigDecimal("-0.01");

        // when & then
        assertThatThrownBy(() -> Quantity.of(count)).isInstanceOf(ValidationException.class);
    }

}
