package com.depromeet.muyaho.domain.memberstock;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberStockAmount {

    @Column(nullable = false)
    private double purchasePrice;

    @Column(nullable = false)
    private double quantity;

    private MemberStockAmount(double purchasePrice, double quantity) {
        validateStockInfo(purchasePrice, quantity);
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }

    private void validateStockInfo(double purchasePrice, double quantity) {
        if (purchasePrice <= 0 || quantity <= 0) {
            throw new IllegalArgumentException(String.format("주식의 평단가 (%s)와 보유 수량 (%s)은 0보다 작을 수 없습니다", purchasePrice, quantity));
        }
    }

    public static MemberStockAmount of(double purchasePrice, double quantity) {
        return new MemberStockAmount(purchasePrice, quantity);
    }

}
