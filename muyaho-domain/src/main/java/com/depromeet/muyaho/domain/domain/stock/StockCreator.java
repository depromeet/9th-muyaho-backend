package com.depromeet.muyaho.domain.domain.stock;

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

    public static Stock createActiveOverseas(String code, String name) {
        return Stock.builder()
            .code(code)
            .name(name)
            .type(StockMarketType.OVERSEAS_STOCK)
            .status(StockStatus.ACTIVE)
            .build();
    }

    public static Stock createActiveDomestic(String code, String name) {
        return Stock.builder()
            .code(code)
            .name(name)
            .type(StockMarketType.DOMESTIC_STOCK)
            .status(StockStatus.ACTIVE)
            .build();
    }

    public static Stock createActiveBitCoin(String code, String name) {
        return Stock.builder()
            .code(code)
            .name(name)
            .type(StockMarketType.BITCOIN)
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
