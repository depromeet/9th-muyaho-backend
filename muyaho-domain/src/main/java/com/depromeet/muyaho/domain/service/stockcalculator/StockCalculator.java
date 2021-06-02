package com.depromeet.muyaho.domain.service.stockcalculator;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;

import java.util.List;

public interface StockCalculator {

    List<StockCalculateResponse> calculateCurrentMemberStocks(Long memberId, StockMarketType type, MemberStockCollection collection);

}
