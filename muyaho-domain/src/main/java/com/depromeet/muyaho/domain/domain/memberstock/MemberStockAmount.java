package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.common.Money;
import com.depromeet.muyaho.domain.domain.common.Quantity;
import com.depromeet.muyaho.domain.domain.stock.Stock;
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
    @AttributeOverride(name = "money", column = @Column(name = "purchase_unit_price"))
    private Money purchaseUnitPrice;

    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "purchase_quantity"))
    private Quantity quantity;

    private MemberStockAmount(BigDecimal purchaseUnitPrice, BigDecimal quantity, CurrencyType currencyType) {
        this.purchaseUnitPrice = Money.of(purchaseUnitPrice, currencyType);
        this.quantity = Quantity.of(quantity);
    }

    public static MemberStockAmount of(Stock stock, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType) {
        stock.validateAllowCurrency(currencyType);
        return new MemberStockAmount(purchasePrice, quantity, currencyType);
    }

    public BigDecimal getPurchaseUnitPrice() {
        return purchaseUnitPrice.getMoney();
    }

    public BigDecimal getQuantity() {
        return quantity.getQuantity();
    }

    public CurrencyType getCurrencyType() {
        return purchaseUnitPrice.getCurrencyType();
    }

}
