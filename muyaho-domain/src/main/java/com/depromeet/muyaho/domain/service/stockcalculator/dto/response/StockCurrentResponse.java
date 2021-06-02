package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
public class StockCurrentResponse {

    private final StockCurrentPriceResponse won;
    private final StockCurrentPriceResponse dollar;

    private StockCurrentResponse(StockCurrentPriceResponse won, StockCurrentPriceResponse dollar) {
        this.won = won;
        this.dollar = dollar;
    }

    public static StockCurrentResponse of(BigDecimal quantity, BigDecimal unitPriceInWon, BigDecimal unitPriceInDollar) {
        return new StockCurrentResponse(
            StockCurrentPriceResponse.of(quantity, unitPriceInWon),
            StockCurrentPriceResponse.of(quantity, unitPriceInDollar)
        );
    }

}
