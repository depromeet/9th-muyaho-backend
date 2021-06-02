package com.depromeet.muyaho.domain.service.memberstock;

import com.depromeet.muyaho.domain.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.service.stockcalculator.StockCalculator;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.service.stockhistory.StockHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberStockRetrieveService {

    private final MemberStockRepository memberStockRepository;
    private final StockCalculator stockCalculator;
    private final StockHistoryService stockHistoryService;

    public List<StockCalculateResponse> getMemberCurrentStocks(StockMarketType type, Long memberId) {
        MemberStockCollection collection = MemberStockCollection.of(memberStockRepository.findAllStocksByMemberIdAndType(memberId, type));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return stockCalculator.calculateCurrentMemberStocks(memberId, type, collection);
    }

    public InvestStatusResponse getMemberInvestStatus(Long memberId) {
        return InvestStatusResponse.of(
            getMemberCurrentStocks(StockMarketType.BITCOIN, memberId),
            getMemberCurrentStocks(StockMarketType.DOMESTIC_STOCK, memberId),
            getMemberCurrentStocks(StockMarketType.OVERSEAS_STOCK, memberId)
        );
    }

    public InvestStatusResponse getLastMemberInvestStatusHistory(Long memberId) {
        return InvestStatusResponse.of(
            stockHistoryService.retrieveMemberStockHistory(StockMarketType.BITCOIN, memberId),
            stockHistoryService.retrieveMemberStockHistory(StockMarketType.DOMESTIC_STOCK, memberId),
            stockHistoryService.retrieveMemberStockHistory(StockMarketType.OVERSEAS_STOCK, memberId)
        );
    }

}
