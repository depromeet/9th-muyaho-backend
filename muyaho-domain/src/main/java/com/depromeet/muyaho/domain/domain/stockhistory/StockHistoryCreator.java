package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;

import java.math.BigDecimal;

public class StockHistoryCreator {

    public static StockHistory create(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        return StockHistory.builder()
            .memberStock(memberStock)
            .currentPriceInWon(currentPriceInWon)
            .currentPriceInDollar(currentPriceInDollar)
            .profitOrLoseRate(profitOrLoseRate)
            .build();
    }

}
