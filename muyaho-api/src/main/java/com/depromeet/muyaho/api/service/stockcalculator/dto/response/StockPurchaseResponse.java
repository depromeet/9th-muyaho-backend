package com.depromeet.muyaho.api.service.stockcalculator.dto.response;


import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
public class StockPurchaseResponse {

    private final String unitPrice;

    private final String amount;

    private final String amountInWon;

    private StockPurchaseResponse(BigDecimal unitPrice, BigDecimal quantity, BigDecimal amountInWon) {
        this.unitPrice = roundFloor(unitPrice);
        this.amount = roundFloor(quantity.multiply(unitPrice));
        this.amountInWon = amountInWon == null ? null : roundFloor(amountInWon);
    }

    public static StockPurchaseResponse of(BigDecimal unitPrice, BigDecimal quantity, BigDecimal amountInWon) {
        return new StockPurchaseResponse(unitPrice, quantity, amountInWon);
    }

}
