package com.depromeet.muyaho.service.stockcalculator;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.dto.response.StockPriceResponse;
import com.depromeet.muyaho.external.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.bitcoin.upbit.dto.response.UpBitTradeInfoResponse;
import com.depromeet.muyaho.service.stockcalculator.dto.response.StockCalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockCalculatorImpl implements StockCalculator {

    private static final String DELIMITER = ",";

    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;

    @Override
    public List<StockCalculateResponse> calculateCurrentStocks(StockMarketType type, MemberStockCollection collection) {
        if (type.isStockType()) {
            return getStockCurrentInfo(collection);
        }
        return getBitCoinCurrentInfo(collection);
    }

    private List<StockCalculateResponse> getStockCurrentInfo(MemberStockCollection collection) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<StockPriceResponse> stockPrices = stockApiCaller.getStockPrice(collection.extractCodesWithDelimiter(DELIMITER));
        return stockPrices.stream()
            .map(tradeInfoResponse -> StockCalculateResponse.of(memberStockMap.get(tradeInfoResponse.getCode()), tradeInfoResponse.getPrice()))
            .collect(Collectors.toList());
    }

    private List<StockCalculateResponse> getBitCoinCurrentInfo(MemberStockCollection collection) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<UpBitTradeInfoResponse> tradeInfoResponses = upBitApiCaller.retrieveTrades(collection.extractCodesWithDelimiter(DELIMITER));
        return tradeInfoResponses.stream()
            .map(tradeInfoResponse -> StockCalculateResponse.of(memberStockMap.get(tradeInfoResponse.getMarket()), tradeInfoResponse.getTradePrice()))
            .collect(Collectors.toList());
    }

}
