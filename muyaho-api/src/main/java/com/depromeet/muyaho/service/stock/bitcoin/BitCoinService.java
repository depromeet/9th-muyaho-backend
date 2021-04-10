package com.depromeet.muyaho.service.stock.bitcoin;

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

    private final UpBitApiCaller upBitApiCaller;

    public List<MarketInfoResponse> retrieveUpBitMarkets() {
        return upBitApiCaller.retrieveMarkets().stream()
            .filter(market -> market.getMarket().startsWith(WON))
            .map(market -> MarketInfoResponse.of(market.getMarket().split(MARKET_SEPARATOR)[1], market.getKoreanName()))
            .collect(Collectors.toList());
    }

    public List<TradeInfoResponse> retrieveUpBitTrade(String marketCodes) {
        return upBitApiCaller.retrieveTrades(toUpBitCode(marketCodes)).stream()
            .map(trade -> TradeInfoResponse.of(trade.getMarket().split(MARKET_SEPARATOR)[1], trade.getTradePrice(), trade.getLowPrice(), trade.getHighPrice()))
            .collect(Collectors.toList());
    }

    private String toUpBitCode(String marketCodes) {
        final String CODE_SEPARATOR = ",";
        return Arrays.stream(marketCodes.split(CODE_SEPARATOR))
            .map(WON::concat)
            .distinct()
            .collect(Collectors.joining(CODE_SEPARATOR));
    }

}
