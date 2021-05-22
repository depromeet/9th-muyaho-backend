package com.depromeet.muyaho.api.service.stockcalculator.dto.response;

import lombok.Getter;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@Getter
public class StockCurrentResponse {

    private final String unitPrice;

    private final String amount;

    private StockCurrentResponse(BigDecimal unitPrice, BigDecimal quantity) {
        this.unitPrice = roundFloor(unitPrice);
        this.amount = roundFloor(quantity.multiply(unitPrice));
    }

    public static StockCurrentResponse of(BigDecimal unitPrice, BigDecimal quantity) {
        return new StockCurrentResponse(unitPrice, quantity);
    }

}
