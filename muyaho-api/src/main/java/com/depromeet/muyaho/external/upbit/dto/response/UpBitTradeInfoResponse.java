package com.depromeet.muyaho.external.upbit.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpBitTradeInfoResponse {

    private String market;

    private double openingPrice;

    private double highPrice;

    private double lowPrice;

    private double tradePrice;

    private double prevClosingPrice;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpBitTradeInfoResponse(String market, double openingPrice, double highPrice, double lowPrice, double tradePrice, double prevClosingPrice) {
        this.market = market;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradePrice = tradePrice;
        this.prevClosingPrice = prevClosingPrice;
    }

}
