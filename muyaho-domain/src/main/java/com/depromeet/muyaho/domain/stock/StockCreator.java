package com.depromeet.muyaho.domain.stock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCreator {

    public static Stock createActive(String code, String name, StockMarketType type) {
        return Stock.builder()
            .type(type)
            .code(code)
            .name(name)
            .status(StockStatus.ACTIVE)
            .build();
    }

    public static Stock createDisable(String code, String name, StockMarketType type) {
        return Stock.builder()
            .type(type)
            .code(code)
            .name(name)
            .status(StockStatus.DISABLED)
            .build();
    }

}
