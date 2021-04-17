package com.depromeet.muyaho.service.stock.bitcoin;

import com.depromeet.muyaho.external.bithumb.BithumbApiCaller;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.service.stock.dto.response.TradeInfoResponse;
import com.depromeet.muyaho.service.stock.dto.response.MarketInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BitCoinService {

    private static final String WON = "KRW-";
    private static final String MARKET_SEPARATOR = "-";
    private static final String MARKET_CODE_SEPARATOR = ",";

    private final UpBitApiCaller upBitApiCaller;
    private final BithumbApiCaller bithumbApiCaller;

    public List<MarketInfoResponse> retrieveUpBitMarkets() {
        return upBitApiCaller.retrieveMarkets().stream()
            .filter(market -> market.getMarket().startsWith(WON))
            .map(market -> MarketInfoResponse.of(market.getMarket().split(MARKET_SEPARATOR)[1], market.getKoreanName()))
            .collect(Collectors.toList());
    }

    // TODO 차후 내부적으로 사용될 수 있게 포맷이 변경되어야 합니다 (테스트용도로만 일단 만들어둔 상황)
    public List<TradeInfoResponse> retrieveUpBitTrade(String marketCodes) {
        return upBitApiCaller.retrieveTrades(splitMarketCodesToUpBit(marketCodes)).stream()
            .map(trade -> TradeInfoResponse.of(trade.getMarket().split(MARKET_SEPARATOR)[1], trade.getTradePrice(), trade.getLowPrice(), trade.getHighPrice()))
            .collect(Collectors.toList());
    }

    private String splitMarketCodesToUpBit(String marketCodes) {
        return Arrays.stream(marketCodes.split(MARKET_CODE_SEPARATOR))
            .map(WON::concat)
            .distinct()
            .collect(Collectors.joining(MARKET_CODE_SEPARATOR));
    }

    // TODO 차후 내부적으로 사용될 수 있게 포맷이 변경되어야 합니다 (테스트용도로만 일단 만들어둔 상황)
    public List<TradeInfoResponse> retrieveBithumbTrade(String marketCodes) {
        return splitMarketCodeToBithumb(marketCodes).stream()
            .map(marketCode -> TradeInfoResponse.of(marketCode, bithumbApiCaller.retrieveTrades(marketCode)))
            .collect(Collectors.toList());
    }

    private List<String> splitMarketCodeToBithumb(String marketCodes) {
        return Arrays.stream(marketCodes.split(MARKET_CODE_SEPARATOR))
            .distinct()
            .collect(Collectors.toList());
    }

}
