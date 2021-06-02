package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCurrentPriceResponse {

    private final String unitPrice;

    private final String amountPrice;

    public static StockCurrentPriceResponse of(BigDecimal quantity, BigDecimal unitPrice) {
        return new StockCurrentPriceResponse(roundFloor(unitPrice), roundFloor(unitPrice.multiply(quantity)));
    }

}
