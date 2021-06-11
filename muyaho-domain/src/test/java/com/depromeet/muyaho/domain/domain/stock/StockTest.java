package com.depromeet.muyaho.domain.domain.stock;

import com.depromeet.muyaho.common.exception.ForbiddenException;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    void 국내주식은_원화를_허용한다() {
        // given
        Stock stock = StockCreator.createActive("code", "국내 주식", StockMarketType.DOMESTIC_STOCK);

        // when
        stock.validateAllowCurrency(CurrencyType.WON);
    }

    @Test
    void 국내주식은_달러를_허용하지_않는다() {
        // given
        Stock stock = StockCreator.createActive("code", "국내 주식", StockMarketType.DOMESTIC_STOCK);

        // when & then
        assertThatThrownBy(() -> stock.validateAllowCurrency(CurrencyType.DOLLAR)).isInstanceOf(ForbiddenException.class);
    }

}
