package com.depromeet.muyaho.domain.memberstock;

import com.depromeet.muyaho.domain.BaseTimeEntity;
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

    @Column(nullable = false)
    private Long stockId;

    @Embedded
    private MemberStockAmount stockAmount;

    private MemberStock(Long memberId, Long stockId, int purchasePrice, int quantity) {
        this.memberId = memberId;
        this.stockId = stockId;
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity);
    }

    public static MemberStock of(Long memberId, Long stockId, int purchasePrice, int quantity) {
        return new MemberStock(memberId, stockId, purchasePrice, quantity);
    }

    public int getPurchasePrice() {
        return this.stockAmount.getPurchasePrice();
    }

    public int getQuantity() {
        return this.stockAmount.getQuantity();
    }

}
