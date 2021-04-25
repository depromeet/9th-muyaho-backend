package com.depromeet.muyaho.domain.memberstock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockCreator {

    public static MemberStock create(Long memberId, Long stockId, int purchasePrice, int quantity) {
        return MemberStock.of(memberId, stockId, purchasePrice, quantity);
    }

}
