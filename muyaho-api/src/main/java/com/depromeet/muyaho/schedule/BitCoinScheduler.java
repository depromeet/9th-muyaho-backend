package com.depromeet.muyaho.schedule;

import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.StockType;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class BitCoinScheduler {

    private final ApplicationEventPublisher eventPublisher;
    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;

    @Scheduled(cron = "0 0 * * * *")
    public void renewUpBitTradingMarketCodes() {
        renewBitCoinCodes();
        renewDomesticStockCodes();
        renewOverseasStockCodes();
    }

    private void renewBitCoinCodes() {
        List<StockInfoRequest> stockInfoList = upBitApiCaller.retrieveMarkets().stream()
            .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.BITCOIN, stockInfoList));
        log.info("비트코인 종목 정보를 갱신합니다. 현재 {} 개의 종목 코드가 거래되고 있습니다", stockInfoList.size());
    }

    private void renewDomesticStockCodes() {
        List<StockInfoRequest> stockInfoList = stockApiCaller.getStockCodes(StockType.DOMESTIC_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.DOMESTIC_STOCK, stockInfoList));
        log.info("국내주식 종목 정보를 갱신합니다. 현재 {} 개의 종목 코드가 거래되고 있습니다", stockInfoList.size());
    }

    private void renewOverseasStockCodes() {
        List<StockInfoRequest> stockInfoList = stockApiCaller.getStockCodes(StockType.OVERSEAS_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.OVERSEAS_STOCK, stockInfoList));
        log.info("해외주식 종목 정보를 갱신합니다. 현재 {} 개의 종목 코드가 거래되고 있습니다", stockInfoList.size());
    }

}
