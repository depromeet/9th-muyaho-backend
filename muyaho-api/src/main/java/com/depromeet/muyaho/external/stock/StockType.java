package com.depromeet.muyaho.external.stock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockType {

    DOMESTIC_STOCK("KRX"),
    OVERSEAS_STOCK("NASDAQ");

    private final String type;

}
