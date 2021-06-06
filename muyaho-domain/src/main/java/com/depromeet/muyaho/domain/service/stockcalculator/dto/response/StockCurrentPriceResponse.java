package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCurrentPriceResponse {

    private String unitPrice;

    private String amountPrice;

    public static StockCurrentPriceResponse of(BigDecimal quantity, BigDecimal unitPrice) {
        return new StockCurrentPriceResponse(roundFloor(unitPrice), roundFloor(unitPrice.multiply(quantity)));
    }

}
