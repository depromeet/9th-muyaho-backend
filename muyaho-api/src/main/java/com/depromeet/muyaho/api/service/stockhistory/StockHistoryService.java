package com.depromeet.muyaho.api.service.stockhistory;

import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.api.service.stockhistory.dto.request.RenewMemberStockHistoryRequest;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    @Transactional(readOnly = true)
    public List<StockCalculateResponse> retrieveMemberStockHistory(StockMarketType type, Long memberId) {
        List<StockHistory> stockHistories = stockHistoryRepository.findAllByMemberIdAndType(memberId, type);
        return stockHistories.stream()
            .map(StockCalculateResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<StockCalculateResponse> renewMemberStockHistory(Long memberId, StockMarketType type, List<RenewMemberStockHistoryRequest> requests) {
        stockHistoryRepository.deleteAll(stockHistoryRepository.findAllByMemberIdAndType(memberId, type));

        List<StockHistory> stockHistories = requests.stream()
            .map(RenewMemberStockHistoryRequest::toEntity)
            .collect(Collectors.toList());

        return stockHistoryRepository.saveAll(stockHistories).stream()
            .map(StockCalculateResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMemberStockHistory(Long memberStockId, Long memberId) {
        stockHistoryRepository.deleteAll(stockHistoryRepository.findAllByMemberStockIdAndMemberId(memberStockId, memberId));
    }

}
