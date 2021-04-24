package com.depromeet.muyaho.domain.stock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCreator {

    public static Stock create(Long memberId, String code, StockType type, int price, int quantity) {
        return Stock.newInstance(memberId, code, type, price, quantity);
    }

}
