package com.depromeet.muyaho.api.service.stock;

import com.depromeet.muyaho.domain.domain.stock.*;
import com.depromeet.muyaho.api.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {

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

    @Transactional(readOnly = true)
    public List<StockInfoResponse> retrieveStockInfo(StockMarketType type) {
        return stockRepository.findAllActiveStockByType(type).stream()
            .map(StockInfoResponse::of)
            .collect(Collectors.toList());
    }

}
