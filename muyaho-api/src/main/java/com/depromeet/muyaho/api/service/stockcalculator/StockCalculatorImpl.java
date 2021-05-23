package com.depromeet.muyaho.api.service.stockcalculator;

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

    @Override
    public List<StockCalculateResponse> calculateCurrentMemberStocks(StockMarketType type, MemberStockCollection collection) {
        BigDecimal rate = exchangeRateApiCaller.fetchExchangeRate();
        if (type.isStockType()) {
            return getStockCurrentInfo(type, collection, rate);
        }
        return getBitCoinCurrentInfo(type, collection, rate);
    }

    private List<StockCalculateResponse> getStockCurrentInfo(StockMarketType type, MemberStockCollection collection, BigDecimal rate) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<StockPriceResponse> currentStockPrices = stockApiCaller.fetchCurrentStockPrice(collection.extractCodesWithDelimiter(DELIMITER));
        return currentStockPrices.stream()
            .map(currentPrice -> StockCalculateResponse.of(
                memberStockMap.get(currentPrice.getCode()),
                calculateCurrentWon(type, currentPrice.getPrice(), rate),
                calculateCurrentDollar(type, currentPrice.getPrice(), rate),
                calculateDifferencePercent(currentPrice.getPrice(), memberStockMap.get(currentPrice.getCode()).getPurchaseUnitPrice())))
            .collect(Collectors.toList());
    }

    private List<StockCalculateResponse> getBitCoinCurrentInfo(StockMarketType type, MemberStockCollection collection, BigDecimal rate) {
        final Map<String, MemberStock> memberStockMap = collection.newMemberStockMap();
        List<UpBitPriceResponse> currentBitcoinPrices = upBitApiCaller.fetchCurrentBitcoinPrice(collection.extractCodesWithDelimiter(DELIMITER));
        return currentBitcoinPrices.stream()
            .map(currentPrice -> StockCalculateResponse.of(
                memberStockMap.get(currentPrice.getMarket()),
                calculateCurrentWon(type, currentPrice.getTradePrice(), rate),
                calculateCurrentDollar(type, currentPrice.getTradePrice(), rate),
                calculateDifferencePercent(currentPrice.getTradePrice(), memberStockMap.get(currentPrice.getMarket()).getPurchaseUnitPrice())))
            .collect(Collectors.toList());
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
