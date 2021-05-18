package com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class BithumbTradeDataResponse {

    private int minPrice;

    private int maxPrice;

    private int openingPrice;

    private int closingPrice;

    @Builder
    public BithumbTradeDataResponse(int minPrice, int maxPrice, int openingPrice, int closingPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
    }

}
