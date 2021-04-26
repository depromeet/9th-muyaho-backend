package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockServiceUtils {

    public static Stock findStockById(StockRepository stockRepository, Long stockId) {
        Stock stock = stockRepository.findStockById(stockId);
        if (stock == null) {
            throw new IllegalArgumentException(String.format("해당하는 주식 (%s)은 존재하지 않습니다", stockId));
        }
        return stock;
    }
}
