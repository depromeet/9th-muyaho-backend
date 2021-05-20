package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.BaseTimeEntity;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private MemberStock(Long memberId, Stock stock, double purchasePrice, double quantity, CurrencyType currencyType) {
        this.memberId = memberId;
        this.stock = stock;
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity, currencyType);
    }

    public static MemberStock of(Long memberId, Stock stock, double purchasePrice, double quantity, CurrencyType currencyType) {
        stock.validateAllowCurrency(currencyType);
        return new MemberStock(memberId, stock, purchasePrice, quantity, currencyType);
    }

    public void updateAmount(double purchasePrice, double quantity, CurrencyType currencyType) {
        this.stockAmount = MemberStockAmount.of(purchasePrice, quantity, currencyType);
    }

    public DeletedMemberStock delete() {
        return DeletedMemberStock.of(id, memberId, stock.getId(), getPurchasePrice().doubleValue(), getQuantity().doubleValue());
    }

    public BigDecimal getPurchasePrice() {
        return this.stockAmount.getPurchasePrice();
    }

    public BigDecimal getQuantity() {
        return this.stockAmount.getQuantity();
    }

    public String getStockCode() {
        return this.stock.getCode();
    }

}
