package com.depromeet.muyaho.service.stock.dto.response;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInfoResponse {

    private final Long id;

    private final String code;

    private final String name;

    private final StockMarketType type;

    public static StockInfoResponse of(Stock stock) {
        return new StockInfoResponse(stock.getId(), stock.getCode(), stock.getName(), stock.getType());
    }

}
