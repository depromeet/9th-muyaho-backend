package com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpBitPriceResponse {

    private String market;

    private BigDecimal openingPrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;

    private BigDecimal tradePrice;

    private BigDecimal prevClosingPrice;

    private UpBitPriceResponse(String market, BigDecimal tradePrice) {
        this.market = market;
        this.tradePrice = tradePrice;
    }

    public static UpBitPriceResponse testInstance(String market, BigDecimal tradePrice) {
        return new UpBitPriceResponse(market, tradePrice);
    }

}
