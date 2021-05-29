package com.depromeet.muyaho.api.service.stockhistory.dto.request;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RenewMemberStockHistoryRequest {

    private MemberStock memberStock;
    private BigDecimal currentPriceInWon;
    private BigDecimal currentPriceInDollar;
    private BigDecimal profitOrLoseRate;

    public static RenewMemberStockHistoryRequest of(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        return new RenewMemberStockHistoryRequest(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    public static RenewMemberStockHistoryRequest testInstance(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        return new RenewMemberStockHistoryRequest(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    public StockHistory toEntity() {
        return StockHistory.builder()
            .memberStock(memberStock)
            .currentPriceInWon(currentPriceInWon)
            .currentPriceInDollar(currentPriceInDollar)
            .profitOrLoseRate(profitOrLoseRate)
            .build();
    }

}
