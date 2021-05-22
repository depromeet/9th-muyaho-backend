package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.common.Money;
import com.depromeet.muyaho.domain.domain.common.Quantity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;

@EqualsAndHashCode
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

    private MemberStockAmount(double purchasePrice, double quantity, CurrencyType currencyType) {
        this.purchasePrice = Money.of(purchasePrice, currencyType);
        this.quantity = Quantity.of(quantity);
    }

    public static MemberStockAmount of(double purchasePrice, double quantity, CurrencyType currencyType) {
        return new MemberStockAmount(purchasePrice, quantity, currencyType);
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice.getMoney();
    }

    public BigDecimal getQuantity() {
        return quantity.getQuantity();
    }

}
