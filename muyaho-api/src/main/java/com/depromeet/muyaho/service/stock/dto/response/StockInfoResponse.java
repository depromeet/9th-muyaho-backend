package com.depromeet.muyaho.service.stock.dto.response;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInfoResponse {

    private final Long stockId;

    private final String stockCode;

    private final StockType type;

    private final int purchasePrice;

    private final int quantity;

    public static StockInfoResponse of(Stock stock) {
        return new StockInfoResponse(stock.getId(), stock.getStockCode(), stock.getType(), stock.getPurchasePrice(), stock.getQuantity());
    }

}
