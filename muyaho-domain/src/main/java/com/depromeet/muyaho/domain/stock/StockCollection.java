package com.depromeet.muyaho.domain.stock;

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

    public void disable() {
        stockList.forEach(Stock::disable);
    }

    public Map<String, Stock> getStockMap() {
        return this.stockList.stream()
            .collect(Collectors.toMap(Stock::getCode, stock -> stock));
    }

}
