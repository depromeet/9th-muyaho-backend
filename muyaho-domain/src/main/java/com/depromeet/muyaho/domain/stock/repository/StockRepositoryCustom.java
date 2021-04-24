package com.depromeet.muyaho.domain.stock.repository;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockType;

import java.util.List;

public interface StockRepositoryCustom {

    List<Stock> findStocksByMemberId(Long memberId);

    Stock findStockByCodeAndTypeAndMemberId(Long memberId, StockType type, String stockCode);

}
