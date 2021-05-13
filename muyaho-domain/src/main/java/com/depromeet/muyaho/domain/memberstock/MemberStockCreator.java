package com.depromeet.muyaho.domain.memberstock;

import com.depromeet.muyaho.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockCreator {

    public static MemberStock create(Long memberId, Stock stock, double purchasePrice, double quantity) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity);
    }

}
