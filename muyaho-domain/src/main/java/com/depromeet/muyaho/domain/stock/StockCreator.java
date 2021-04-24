package com.depromeet.muyaho.domain.stock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCreator {

    public static Stock createActive(String code, String name, StockMarketType type) {
        return new Stock(type, code, name, StockStatus.ACTIVE);
    }

    public static Stock createDisable(String code, String name, StockMarketType type) {
        return new Stock(type, code, name, StockStatus.DISABLED);
    }

}
