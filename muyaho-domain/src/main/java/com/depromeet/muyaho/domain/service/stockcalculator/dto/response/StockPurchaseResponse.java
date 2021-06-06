package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockPurchaseResponse {

    private String unitPrice;

    private String amount;

    private String amountInWon;

    private StockPurchaseResponse(BigDecimal unitPrice, BigDecimal quantity, BigDecimal amountInWon) {
        this.unitPrice = roundFloor(unitPrice);
        this.amount = roundFloor(quantity.multiply(unitPrice));
        this.amountInWon = amountInWon == null ? null : roundFloor(amountInWon);
    }

    public static StockPurchaseResponse of(BigDecimal unitPrice, BigDecimal quantity, BigDecimal amountInWon) {
        return new StockPurchaseResponse(unitPrice, quantity, amountInWon);
    }

}
