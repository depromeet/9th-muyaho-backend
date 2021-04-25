package com.depromeet.muyaho.domain.memberstock;

import com.depromeet.muyaho.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberStock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @JoinColumn(name = "stock_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;

    @Embedded
    private MemberStockAmount stockAmount;

    private MemberStock(Long memberId, Stock stock, int purchasePrice, int quantity) {
        this.memberId = memberId;
        this.stock = stock;
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity);
    }

    public static MemberStock of(Long memberId, Stock stock, int purchasePrice, int quantity) {
        return new MemberStock(memberId, stock, purchasePrice, quantity);
    }

    public int getPurchasePrice() {
        return this.stockAmount.getPurchasePrice();
    }

    public int getQuantity() {
        return this.stockAmount.getQuantity();
    }

}
