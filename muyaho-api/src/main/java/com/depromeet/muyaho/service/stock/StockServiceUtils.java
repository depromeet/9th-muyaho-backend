package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.stock.StockType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class StockServiceUtils {

    static void validateNotExistsStock(StockRepository stockRepository, Long memberId, StockType type, String stockCode) {
        Stock stock = stockRepository.findStockByCodeAndTypeAndMemberId(memberId, type, stockCode);
        if (stock != null) {
            throw new IllegalArgumentException(String.format("이미 멤버 (%s)가 등록한 주식 (%s - %s)입니다", memberId, type, stockCode));
        }
    }

}
