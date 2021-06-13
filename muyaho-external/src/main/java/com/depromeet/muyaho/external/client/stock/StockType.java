package com.depromeet.muyaho.external.client.stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockType {

    DOMESTIC_STOCK("KRX"),
    NASDAQ("NASDAQ"),
    NYSE("NYSE");

    private final String type;

}
