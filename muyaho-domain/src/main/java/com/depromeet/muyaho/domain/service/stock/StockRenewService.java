package com.depromeet.muyaho.domain.service.stock;

import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCollection;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockRenewService {

    private final StockRepository stockRepository;

    @Transactional
    public void renewStock(StockMarketType type, List<StockInfoRequest> stockInfos) {
        StockCollection collection = StockCollection.of(stockRepository.findAllByType(type));
        stockRepository.saveAll(activateListedStocks(collection, type, stockInfos));
    }

    private List<Stock> activateListedStocks(StockCollection collection, StockMarketType type, List<StockInfoRequest> stockInfos) {
        Map<String, Stock> presentStocks = collection.toDisableMap();
        return stockInfos.stream()
            .filter(request -> request.isAllowed(type))
            .map(request -> presentStocks.getOrDefault(request.getCode(), request.toEntity(type)))
            .map(Stock::active)
            .collect(Collectors.toList());
    }

}
