package com.depromeet.muyaho.domain.domain.stock.repository;

import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;

import java.util.List;

public interface StockRepositoryCustom {

    List<Stock> findAllByType(StockMarketType type);

    List<Stock> findAllActiveStockByType(StockMarketType type);

    void refreshCache(StockMarketType type);

    Stock findActiveStockById(Long stockId);

}
