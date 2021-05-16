package com.depromeet.muyaho.service.stockcalculator;

import com.depromeet.muyaho.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stockcalculator.dto.response.StockCalculateResponse;

import java.util.List;

public interface StockCalculator {

    List<StockCalculateResponse> calculateCurrentStocks(StockMarketType type, MemberStockCollection collection);

}
