package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
