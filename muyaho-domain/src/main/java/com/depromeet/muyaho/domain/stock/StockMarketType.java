package com.depromeet.muyaho.domain.stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum StockMarketType {

    DOMESTIC_STOCK(Collections.emptyList()),
    OVERSEAS_STOCK(Collections.emptyList()),
    BITCOIN(Collections.singletonList("KRW-"));

    private final List<String> allowedCurrencies;

    public boolean isAllowAll() {
        return this.allowedCurrencies.isEmpty();
    }

}
