package com.depromeet.muyaho.service.stock.dto.response;

import com.depromeet.muyaho.external.bithumb.dto.response.BithumbTradeInfoResponse;
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

    public static TradeInfoResponse of(String market, BithumbTradeInfoResponse bithumbTradeInfo) {
        return new TradeInfoResponse(market, bithumbTradeInfo.getClosingPrice(), bithumbTradeInfo.getMinPrice(), bithumbTradeInfo.getMaxPrice());
    }

}
