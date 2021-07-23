package com.depromeet.muyaho.domain.service.stockhistory;

import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.service.stockhistory.dto.request.RenewMemberStockHistoryRequest;
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
        List<StockHistory> lastHistories = stockHistoryRepository.findAllByMemberIdAndType(memberId, type);

        List<StockHistory> currentStockHistories = requests.stream()
            .map(RenewMemberStockHistoryRequest::toEntity)
            .collect(Collectors.toList());

        // 1. 기존의 히스토리에서 현재 남아 있지 않는 히스토리를 제거한다.
        List<StockHistory> removedHistories = lastHistories.stream()
            .filter(lastHistory -> !currentStockHistories.contains(lastHistory))
            .collect(Collectors.toList());
        stockHistoryRepository.deleteAll(removedHistories);

        // 2. 새롭게 생성된 히스토리들을 추가한다.
        List<StockHistory> newHistories = currentStockHistories.stream()
            .filter(currentHistory -> !lastHistories.contains(currentHistory))
            .collect(Collectors.toList());
        stockHistoryRepository.saveAll(newHistories);

        return currentStockHistories.stream()
            .map(StockCalculateResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMemberStockHistory(Long memberStockId, Long memberId) {
        stockHistoryRepository.deleteAll(stockHistoryRepository.findAllByMemberStockIdAndMemberId(memberStockId, memberId));
    }

}
