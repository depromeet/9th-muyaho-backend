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

    @Embedded
    private MemberStockSeedPrice purchaseTotalPriceInWon;

    private MemberStock(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal purchaseTotalPriceInWon) {
        this.memberId = memberId;
        this.stock = stock;
        this.stockAmount = MemberStockAmount.of(stock, purchasePrice, quantity, currencyType);
        this.purchaseTotalPriceInWon = MemberStockSeedPrice.of(stock, purchaseTotalPriceInWon);
    }

    public static MemberStock of(Long memberId, Stock stock, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal purchaseTotalPrice) {
        return new MemberStock(memberId, stock, purchasePrice, quantity, currencyType, purchaseTotalPrice);
    }

    public void updateAmount(BigDecimal purchasePrice, BigDecimal quantity, BigDecimal totalPurchasePrice) {
        this.stockAmount = MemberStockAmount.of(stock, purchasePrice, quantity, getCurrencyType());
        this.purchaseTotalPriceInWon = MemberStockSeedPrice.of(this.stock, totalPurchasePrice);
    }

    public DeletedMemberStock delete() {
        return DeletedMemberStock.of(id, memberId, stock, getPurchaseUnitPrice(), getQuantity(), getCurrencyType(), purchaseTotalPriceInWon == null ? null : purchaseTotalPriceInWon.getTotalPurchasePriceInWon());
    }

    public BigDecimal getPurchaseUnitPrice() {
        return this.stockAmount.getPurchaseUnitPrice();
    }

    public BigDecimal getQuantity() {
        return this.stockAmount.getQuantity();
    }

    public String getStockCode() {
        return this.stock.getCode();
    }

    public CurrencyType getCurrencyType() {
        return this.stockAmount.getCurrencyType();
    }

    public BigDecimal getPurchaseTotalPriceInWon() {
        if (purchaseTotalPriceInWon == null) {
            return null;
        }
        return purchaseTotalPriceInWon.getTotalPurchasePriceInWon();
    }

}
