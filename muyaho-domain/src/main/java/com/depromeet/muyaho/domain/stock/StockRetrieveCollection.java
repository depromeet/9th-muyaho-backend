package com.depromeet.muyaho.domain.stock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockRetrieveCollection {

    private final Map<Long, Stock> stockMap = new HashMap<>();

    private StockRetrieveCollection(Map<Long, Stock> stockMap) {
        this.stockMap.putAll(stockMap);
    }

    public static StockRetrieveCollection of(List<Stock> stockList) {
        return new StockRetrieveCollection(stockList.stream()
            .collect(Collectors.toMap(Stock::getId, stock -> stock)));
    }

    public Stock getStock(Long stockId) {
        return this.stockMap.get(stockId);
    }

}
