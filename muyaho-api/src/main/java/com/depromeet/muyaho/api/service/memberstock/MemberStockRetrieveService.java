package com.depromeet.muyaho.api.service.memberstock;

import com.depromeet.muyaho.api.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.service.stockcalculator.StockCalculator;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberStockRetrieveService {

    private final MemberStockRepository memberStockRepository;
    private final StockCalculator stockCalculator;

    public List<StockCalculateResponse> getMemberCurrentStocks(StockMarketType type, Long memberId) {
        MemberStockCollection collection = MemberStockCollection.of(memberStockRepository.findAllStocksByMemberIdAndType(memberId, type));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return stockCalculator.calculateCurrentMemberStocks(type, collection);
    }

    public InvestStatusResponse getMemberInvestStatus(Long memberId) {
        return InvestStatusResponse.of(
            getMemberCurrentStocks(StockMarketType.BITCOIN, memberId),
            getMemberCurrentStocks(StockMarketType.DOMESTIC_STOCK, memberId),
            getMemberCurrentStocks(StockMarketType.OVERSEAS_STOCK, memberId)
        );
    }

}
