package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeletedMemberStock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long backupId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long stockId;

    @Embedded
    private MemberStockAmount stockAmount;

    private MemberStockSeedPrice totalPurchasePrice;

    @Builder
    public DeletedMemberStock(Stock stock, Long backupId, Long memberId, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal totalPurchasePrice) {
        this.backupId = backupId;
        this.memberId = memberId;
        this.stockId = stock.getId();
        this.stockAmount = MemberStockAmount.of(stock, purchasePrice, quantity, currencyType);
        this.totalPurchasePrice = MemberStockSeedPrice.of(stock, totalPurchasePrice);
    }

    public static DeletedMemberStock of(Long backupId, Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal totalPurchasePrice) {
        return DeletedMemberStock.builder()
            .backupId(backupId)
            .stock(stock)
            .memberId(memberId)
            .purchasePrice(purchasePrice)
            .quantity(quantity)
            .totalPurchasePrice(totalPurchasePrice)
            .currencyType(currencyType)
            .build();
    }

}
