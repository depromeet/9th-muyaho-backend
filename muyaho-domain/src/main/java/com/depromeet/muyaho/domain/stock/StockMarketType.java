package com.depromeet.muyaho.domain.stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum StockMarketType {

    DOMESTIC_STOCK(StockType.STOCK, Collections.emptyList()),
    OVERSEAS_STOCK(StockType.STOCK, Collections.emptyList()),
    BITCOIN(StockType.BITCOIN, Collections.singletonList("KRW-"));

    public boolean isAllowedCode(String code) {
        if (allowedCurrencies.isEmpty()) {
            return true;
        }
        return allowedCurrencies.stream()
            .anyMatch(code::startsWith);
    }

    public boolean isStockType() {
        return this.type.equals(StockType.STOCK);
    }

    private final StockType type;
    private final List<String> allowedCurrencies;

    private enum StockType {
        STOCK,
        BITCOIN
    }

}
