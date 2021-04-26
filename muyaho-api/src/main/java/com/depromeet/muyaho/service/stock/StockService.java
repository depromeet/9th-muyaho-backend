package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockCollection;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
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
        StockCollection stockCollection = StockCollection.of(stockRepository.findAllByType(type));
        stockCollection.disable();

        final Map<String, Stock> stockMap = stockCollection.getStockMap();
        List<Stock> stockList = stockInfos.stream()
            .map(stock -> stockMap.getOrDefault(stock.getCode(), Stock.newInstance(type, stock.getCode(), stock.getName())))
            .map(Stock::active)
            .collect(Collectors.toList());
        
        stockRepository.saveAll(stockList);
    }

    @Transactional(readOnly = true)
    public List<StockInfoResponse> retrieveStockInfo(StockMarketType type) {
        return stockRepository.findAllActiveStockByType(type).stream()
            .map(StockInfoResponse::of)
            .collect(Collectors.toList());
    }

}