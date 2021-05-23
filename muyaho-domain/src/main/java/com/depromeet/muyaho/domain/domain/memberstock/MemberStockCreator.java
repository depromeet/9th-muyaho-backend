package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockCreator {

    public static MemberStock create(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, CurrencyType.WON, null);
    }

    public static MemberStock create(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType type) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, type, BigDecimal.TEN);
    }

    public static MemberStock createDollar(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, CurrencyType.DOLLAR, BigDecimal.TEN);
    }

    public static MemberStock createWon(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, CurrencyType.WON, null);
    }

}
