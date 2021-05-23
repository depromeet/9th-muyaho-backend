package com.depromeet.muyaho.api.service.stock.dto.request;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveStocksRequest {

    @NotNull
    private StockMarketType type;

    public static RetrieveStocksRequest testInstance(StockMarketType type) {
        return new RetrieveStocksRequest(type);
    }

}
