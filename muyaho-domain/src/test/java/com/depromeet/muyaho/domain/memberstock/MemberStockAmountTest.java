package com.depromeet.muyaho.domain.memberstock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberStockAmountTest {

    @Test
    void 주식의_평단가는_0_이하_일수_없다() {
        // given
        int purchasePrice = 0;
        // when & then
        assertThatThrownBy(() -> MemberStockAmount.of(purchasePrice, 1000)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주식_보류_수량은_0_이하_일수_없다() {
        // given
        int quantity = 0;

        // when & then
        assertThatThrownBy(() -> MemberStockAmount.of(1000, quantity)).isInstanceOf(IllegalArgumentException.class);
    }

}
