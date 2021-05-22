package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Builder
    public DeletedMemberStock(Long backupId, Long memberId, Long stockId, double purchasePrice, double quantity, CurrencyType currencyType) {
        this.backupId = backupId;
        this.memberId = memberId;
        this.stockId = stockId;
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity, currencyType);
    }

    public static DeletedMemberStock of(Long backupId, Long memberId, Long stockId, double purchasePrice, double quantity) {
        return DeletedMemberStock.builder()
            .backupId(backupId)
            .memberId(memberId)
            .stockId(stockId)
            .purchasePrice(purchasePrice)
            .quantity(quantity)
            .build();
    }

}
