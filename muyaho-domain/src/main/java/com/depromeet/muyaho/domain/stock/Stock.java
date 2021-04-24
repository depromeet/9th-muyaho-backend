package com.depromeet.muyaho.domain.stock;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private StockType type;

    @Column(nullable = false)
    private String stockCode;

    @Embedded
    private StockAmount stockAmount;

    @Builder
    private Stock(Long memberId, StockType type, String stockCode, int purchasePrice, int quantity) {
        this.memberId = memberId;
        this.type = type;
        this.stockCode = stockCode;
        this.stockAmount = StockAmount.of(purchasePrice, quantity);
    }

    public static Stock newInstance(Long memberId, String stockCode, StockType type, int purchasePrice, int quantity) {
        return Stock.builder()
            .memberId(memberId)
            .type(type)
            .stockCode(stockCode)
            .purchasePrice(purchasePrice)
            .quantity(quantity)
            .build();
    }

    public int getPurchasePrice() {
        return this.stockAmount.getPurchasePrice();
    }

    public int getQuantity() {
        return this.stockAmount.getQuantity();
    }

}
