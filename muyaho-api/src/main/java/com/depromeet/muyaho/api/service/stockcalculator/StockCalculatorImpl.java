package com.depromeet.muyaho.api.service.stockcalculator;

import com.depromeet.muyaho.api.service.stockhistory.StockHistoryService;
import com.depromeet.muyaho.api.service.stockhistory.dto.request.RenewMemberStockHistoryRequest;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.external.client.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitPriceResponse;
import com.depromeet.muyaho.external.client.currency.ExchangeRateApiCaller;
import com.depromeet.muyaho.external.client.stock.StockApiCaller;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.calculateDifferencePercent;

@RequiredArgsConstructor
@Service
public class StockCalculatorImpl implements StockCalculator {

    private static final String DELIMITER = ",";

    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;
    private final ExchangeRateApiCaller exchangeRateApiCaller;
    private final StockHistoryService stockHistoryService;

    @Override
    public List<StockCalculateResponse> calculateCurrentMemberStocks(Long memberId, StockMarketType type, MemberStockCollection collection) {
        BigDecimal rate = exchangeRateApiCaller.fetchExchangeRate();
        if (type.isStockType()) {
            return getStockCurrentStatus(memberId, type, collection, rate);
        }
        return getBitCoinCurrentStatus(memberId, type, collection, rate);
    }

    private List<StockCalculateResponse> getStockCurrentStatus(Long memberId, StockMarketType type, MemberStockCollection collection, BigDecimal rate) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<StockPriceResponse> currentStockPrices = stockApiCaller.fetchCurrentStockPrice(collection.extractCodesWithDelimiter(DELIMITER));

        List<RenewMemberStockHistoryRequest> renewMemberStockHistoryRequests = currentStockPrices.stream()
            .map(currentPrice -> RenewMemberStockHistoryRequest.of(
                memberStockMap.get(currentPrice.getCode()),
                calculateCurrentWon(type, currentPrice.getPrice(), rate),
                calculateCurrentDollar(type, currentPrice.getPrice(), rate),
                calculateDifferencePercent(currentPrice.getPrice(), memberStockMap.get(currentPrice.getCode()).getPurchaseUnitPrice()))
            ).collect(Collectors.toList());

        return stockHistoryService.renewMemberStockHistory(memberId, type, renewMemberStockHistoryRequests);
    }

    private List<StockCalculateResponse> getBitCoinCurrentStatus(Long memberId, StockMarketType type, MemberStockCollection collection, BigDecimal rate) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<UpBitPriceResponse> currentBitcoinPrices = upBitApiCaller.fetchCurrentBitcoinPrice(collection.extractCodesWithDelimiter(DELIMITER));

        List<RenewMemberStockHistoryRequest> renewMemberStockHistoryRequests = currentBitcoinPrices.stream()
            .map(currentPrice -> RenewMemberStockHistoryRequest.of(
                memberStockMap.get(currentPrice.getMarket()),
                calculateCurrentWon(type, currentPrice.getTradePrice(), rate),
                calculateCurrentDollar(type, currentPrice.getTradePrice(), rate),
                calculateDifferencePercent(currentPrice.getTradePrice(), memberStockMap.get(currentPrice.getMarket()).getPurchaseUnitPrice())))
            .collect(Collectors.toList());

        return stockHistoryService.renewMemberStockHistory(memberId, type, renewMemberStockHistoryRequests);
    }

    private BigDecimal calculateCurrentWon(StockMarketType type, BigDecimal currentPrice, BigDecimal rate) {
        if (type.isTradeByDollars()) {
            return currentPrice.multiply(rate);
        }
        return currentPrice;
    }

    private BigDecimal calculateCurrentDollar(StockMarketType type, BigDecimal currentPrice, BigDecimal rate) {
        if (type.isTradeByDollars()) {
            return currentPrice;
        }
        return currentPrice.divide(rate, new MathContext(2));
    }

}
