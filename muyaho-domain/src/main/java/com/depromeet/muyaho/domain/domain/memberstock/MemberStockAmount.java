package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.common.Money;
import com.depromeet.muyaho.domain.domain.common.Quantity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberStockAmount {

    @Embedded
    @AttributeOverride(name = "money", column = @Column(name = "purchase_price"))
    private Money purchasePrice;

    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "purchase_quantity"))
    private Quantity quantity;

    private MemberStockAmount(double purchasePrice, double quantity) {
        this.purchasePrice = Money.of(purchasePrice);
        this.quantity = Quantity.of(quantity);
    }

    public static MemberStockAmount of(double purchasePrice, double quantity) {
        return new MemberStockAmount(purchasePrice, quantity);
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice.getMoney();
    }

    public BigDecimal getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberStockAmount amount = (MemberStockAmount) o;
        return Objects.equals(purchasePrice, amount.purchasePrice) && Objects.equals(quantity, amount.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchasePrice, quantity);
    }

}
