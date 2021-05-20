package com.depromeet.muyaho.api.service.stock;

import com.depromeet.muyaho.domain.domain.stock.*;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
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
    public List<StockInfoResponse> retrieveStockInfo(StockMarketType type) {
        return stockRepository.findAllActiveStockByType(type).stream()
            .map(StockInfoResponse::of)
            .collect(Collectors.toList());
    }

}
