package com.depromeet.muyaho.domain.stock.repository;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockMarketType;

import java.util.List;

public interface StockRepositoryCustom {

    List<Stock> findAllByType(StockMarketType type);

    List<Stock> findAllActiveStockByType(StockMarketType type);

    Stock findStockById(Long stockId);

    List<Stock> findAllByIds(List<Long> stockIds);

}
