package com.depromeet.muyaho.api.service.stock.dto.response;

import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInfoResponse {

    private Long id;

    private String code;

    private String name;

    private StockMarketType type;

    public static StockInfoResponse of(Stock stock) {
        return new StockInfoResponse(stock.getId(), stock.getCode(), stock.getName(), stock.getType());
    }

}
