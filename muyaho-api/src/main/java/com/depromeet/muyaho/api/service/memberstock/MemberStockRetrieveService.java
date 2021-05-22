package com.depromeet.muyaho.api.service.memberstock;

import com.depromeet.muyaho.api.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.api.service.memberstock.dto.response.OverSeaCalculateResponse;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.service.stockcalculator.StockCalculator;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.external.client.currency.CurrencyRateApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberStockRetrieveService {

    private final CurrencyRateApiCaller currencyRateApiCaller;
    private final MemberStockRepository memberStockRepository;
    private final StockCalculator stockCalculator;

    public List<StockCalculateResponse> getMemberCurrentStocks(StockMarketType type, Long memberId) {
        MemberStockCollection collection = MemberStockCollection.of(memberStockRepository.findAllStocksByMemberIdAndType(memberId, type));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return stockCalculator.calculateCurrentStocks(type, collection);
    }

    public InvestStatusResponse getAllStockStatus(Long memberId) {
        return InvestStatusResponse.of(
            getMemberCurrentStocks(StockMarketType.BITCOIN, memberId),
            getMemberCurrentStocks(StockMarketType.DOMESTIC_STOCK, memberId),
            exchangeDollarsToWon(getMemberCurrentStocks(StockMarketType.OVERSEAS_STOCK, memberId))
        );
    }

    private List<OverSeaCalculateResponse> exchangeDollarsToWon(List<StockCalculateResponse> stockCalculateResponses) {
        final BigDecimal rate = currencyRateApiCaller.getCurrentRate();
        return stockCalculateResponses.stream()
            .map(dollar -> OverSeaCalculateResponse.of(dollar, dollar.getCurrentUnitPrice().multiply(rate)))
            .collect(Collectors.toList());
    }

}
