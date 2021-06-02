package com.depromeet.muyaho.domain.external.stock.dto.response;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockPriceResponse {

    private String code;

    private BigDecimal price;

    public static StockPriceResponse testInstance(String code, BigDecimal price) {
        return new StockPriceResponse(code, price);
    }

}
