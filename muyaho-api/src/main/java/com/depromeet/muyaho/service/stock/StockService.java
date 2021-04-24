package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.service.stock.dto.request.AddStockRequest;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void addStock(AddStockRequest request, Long memberId) {
        // TODO: 해당 타입(국내 주식, 비트코인 등)에 해당 코드가 유효한지 체크
        StockServiceUtils.validateNotExistsStock(stockRepository, memberId, request.getType(), request.getStockCode());
        stockRepository.save(request.toEntity(memberId));
    }

    @Transactional(readOnly = true)
    public List<StockInfoResponse> retrieveMyStocks(Long memberId) {
        return stockRepository.findStocksByMemberId(memberId).stream()
            .map(StockInfoResponse::of)
            .collect(Collectors.toList());
    }

}
