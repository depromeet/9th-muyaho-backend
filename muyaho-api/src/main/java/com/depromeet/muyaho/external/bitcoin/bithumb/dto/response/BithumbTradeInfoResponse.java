package com.depromeet.muyaho.external.bitcoin.bithumb.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BithumbTradeInfoResponse {

    private BithumbTradeDataResponse data;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public BithumbTradeInfoResponse(int minPrice, int maxPrice, int closingPrice, int openingPrice) {
        this.data = BithumbTradeDataResponse.builder()
            .minPrice(minPrice)
            .maxPrice(maxPrice)
            .closingPrice(closingPrice)
            .openingPrice(openingPrice)
            .build();
    }

    public int getMinPrice() {
        return this.data.getMinPrice();
    }

    public int getMaxPrice() {
        return this.data.getMaxPrice();
    }

    public int getClosingPrice() {
        return this.data.getClosingPrice();
    }

}
