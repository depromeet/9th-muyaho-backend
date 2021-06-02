package com.depromeet.muyaho.domain.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class OverViewStocksResponse {

    private final List<StockCalculateResponse> bitCoins = new ArrayList<>();
    private final List<StockCalculateResponse> domesticStocks = new ArrayList<>();
    private final List<StockCalculateResponse> foreignStocks = new ArrayList<>();

    private OverViewStocksResponse(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        this.bitCoins.addAll(bitCoinCurrentInfo);
        this.domesticStocks.addAll(domesticCurrentInfo);
        this.foreignStocks.addAll(foreignStocks);
    }

    public static OverViewStocksResponse of(List<StockCalculateResponse> bitCoinCurrentInfo, List<StockCalculateResponse> domesticCurrentInfo, List<StockCalculateResponse> foreignStocks) {
        return new OverViewStocksResponse(bitCoinCurrentInfo, domesticCurrentInfo, foreignStocks);
    }

}
