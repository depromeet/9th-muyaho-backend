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
@Table(
    uniqueConstraints = @UniqueConstraint(name = "uni_member_stock_1", columnNames = {"memberId", "stock_id"})
)
public class MemberStock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
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

    public void updateAmount(int purchasePrice, int quantity) {
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity);
    }

    public DeletedMemberStock delete() {
        return DeletedMemberStock.of(id, memberId, stock.getId(), getPurchasePrice(), getQuantity());
    }

    public int getPurchasePrice() {
        return this.stockAmount.getPurchasePrice();
    }

    public int getQuantity() {
        return this.stockAmount.getQuantity();
    }

}
