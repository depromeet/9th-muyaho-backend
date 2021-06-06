package com.depromeet.muyaho.domain.service.stock.dto.request;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStocksRequest {

    @NotNull(message = "{stock.marketType.notnull}")
    private StockMarketType type;

    public static RetrieveStocksRequest testInstance(StockMarketType type) {
        return new RetrieveStocksRequest(type);
    }

}
