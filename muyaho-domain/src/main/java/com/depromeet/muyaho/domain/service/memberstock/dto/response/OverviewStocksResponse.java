package com.depromeet.muyaho.domain.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OverviewStocksResponse {

    private List<StockCalculateResponse> bitCoins;
    private List<StockCalculateResponse> domesticStocks;
    private List<StockCalculateResponse> foreignStocks;

    private OverviewStocksResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        this.bitCoins = bitCoinCurrentInfo;
        this.domesticStocks = domesticCurrentInfo;
        this.foreignStocks = foreignStocks;
    }

    public static OverviewStocksResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        return new OverviewStocksResponse(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
    }

}
