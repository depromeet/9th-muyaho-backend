package com.depromeet.muyaho.service.stock.dto.response;

import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TradeInfoResponse {

    private final String market;

    private final int tradePrice;

    private final int lowPrice;

    private final int highPrice;

    public static TradeInfoResponse of(String market, int price, int lowPrice, int highPrice) {
        return new TradeInfoResponse(market, price, lowPrice, highPrice);
    }

}
