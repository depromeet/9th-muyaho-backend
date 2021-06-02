package com.depromeet.muyaho.domain.service.stock;

import com.depromeet.muyaho.domain.service.stock.dto.request.RetrieveStocksRequest;
import com.depromeet.muyaho.domain.domain.stock.*;
import com.depromeet.muyaho.domain.service.stock.dto.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public List<StockInfoResponse> retrieveStocks(RetrieveStocksRequest request) {
        List<Stock> stocks = stockRepository.findAllActiveStockByType(request.getType());
        return stocks.stream()
            .map(StockInfoResponse::of)
            .collect(Collectors.toList());
    }

}
