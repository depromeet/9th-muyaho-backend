package com.depromeet.muyaho.domain.stock;

/**
 * 비트 코인의 경우 거래소마다 현재가가 모두 달라서 다르게 가져가야 할 것으로 보입니다.
 */
public enum StockMarketType {

    DOMESTIC_STOCK,
    OVERSEAS_STOCK,
    BITCOIN_UPBIT

}
