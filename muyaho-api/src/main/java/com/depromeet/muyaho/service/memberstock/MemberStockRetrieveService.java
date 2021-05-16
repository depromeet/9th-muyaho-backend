package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stockcalculator.StockCalculator;
import com.depromeet.muyaho.service.stockcalculator.dto.response.StockCalculateResponse;
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
        return stockCalculator.calculateCurrentStocks(type, collection);
    }

}
