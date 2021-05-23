package com.depromeet.muyaho.api.service.stockcalculator;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;

import java.util.List;

public interface StockCalculator {

    List<StockCalculateResponse> calculateCurrentMemberStocks(StockMarketType type, MemberStockCollection collection);

}
