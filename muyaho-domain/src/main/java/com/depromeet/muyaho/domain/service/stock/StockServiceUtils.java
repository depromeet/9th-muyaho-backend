package com.depromeet.muyaho.domain.service.stock;

import com.depromeet.muyaho.common.exception.NotFoundException;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockServiceUtils {

    public static Stock findActiveStockById(StockRepository stockRepository, Long stockId) {
        Stock stock = stockRepository.findActiveStockById(stockId);
        if (stock == null) {
            throw new NotFoundException(String.format("해당하는 주식 (%s)은 존재하지 않습니다", stockId));
        }
        return stock;
    }

}
