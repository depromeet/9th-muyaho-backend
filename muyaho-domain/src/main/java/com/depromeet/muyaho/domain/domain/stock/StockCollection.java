package com.depromeet.muyaho.domain.domain.stock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCollection {

    private final List<Stock> stockList = new ArrayList<>();

    private StockCollection(List<Stock> stockList) {
        this.stockList.addAll(stockList);
    }

    public static StockCollection of(List<Stock> stockList) {
        return new StockCollection(stockList);
    }

    public Map<String, Stock> toDisableMap() {
        return this.stockList.stream()
            .map(Stock::disable)
            .collect(Collectors.toMap(Stock::getCode, stock -> stock));
    }

}
