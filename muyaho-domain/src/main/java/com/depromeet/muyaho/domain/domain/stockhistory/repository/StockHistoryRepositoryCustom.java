package com.depromeet.muyaho.domain.domain.stockhistory.repository;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;

import java.util.List;

public interface StockHistoryRepositoryCustom {

    List<StockHistory> findAllByMemberIdAndType(Long memberId, StockMarketType type);

}
