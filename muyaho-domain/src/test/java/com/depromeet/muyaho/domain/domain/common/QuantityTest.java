package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.domain.common.Quantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuantityTest {

    @Test
    void Quantity_는_보유수량을_나타내는_객체이며_소수도_가능하다() {
        // given
        double count = 3;

        // when
        Quantity quantity = Quantity.of(count);

        // then
        assertThat(quantity.getQuantity()).isEqualByComparingTo(new BigDecimal(count));
    }

    @Test
    void 보유수량이_0일경우_에러가_발생한다() {
        // given
        double count = 0;

        // when & then
        assertThatThrownBy(() -> Quantity.of(count)).isInstanceOf(ValidationException.class);
    }

}
