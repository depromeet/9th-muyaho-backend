package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCurrentResponse {

    private StockCurrentPriceResponse won;
    private StockCurrentPriceResponse dollar;

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
