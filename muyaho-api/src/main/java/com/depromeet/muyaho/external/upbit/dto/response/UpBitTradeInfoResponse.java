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

    private int openingPrice;

    private int highPrice;

    private int lowPrice;

    private int tradePrice;

    private int prevClosingPrice;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public UpBitTradeInfoResponse(String market, int openingPrice, int highPrice, int lowPrice, int tradePrice, int prevClosingPrice, int changeRate) {
        this.market = market;
        this.openingPrice = openingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.tradePrice = tradePrice;
        this.prevClosingPrice = prevClosingPrice;
    }

}
