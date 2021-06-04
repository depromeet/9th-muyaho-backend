package com.depromeet.muyaho.domain.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class OverviewStocksResponse {

    private final List<StockCalculateResponse> bitCoins = new ArrayList<>();
    private final List<StockCalculateResponse> domesticStocks = new ArrayList<>();
    private final List<StockCalculateResponse> foreignStocks = new ArrayList<>();

    private OverviewStocksResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        this.bitCoins.addAll(bitCoinCurrentInfo);
        this.domesticStocks.addAll(domesticCurrentInfo);
        this.foreignStocks.addAll(foreignStocks);
    }

    public static OverviewStocksResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        return new OverviewStocksResponse(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
    }

}
