package com.depromeet.muyaho.schedule;

import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.StockType;
import com.depromeet.muyaho.external.bitcoin.upbit.UpBitApiCaller;
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
public class StockScheduler {

    private final ApplicationEventPublisher eventPublisher;
    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;

    @Scheduled(cron = "0 0 0 * * *")
    public void renewStocksCode() {
        renewBitCoinStocksCode();
        renewDomesticStocksCode();
        renewOverseasStocksCode();
    }

    private void renewBitCoinStocksCode() {
        log.info("비트코인 실시간 종목 코드 및 종목명을 갱신합니다.");
        List<StockInfoRequest> bitCoinStocks = upBitApiCaller.retrieveMarkets().stream()
            .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.BITCOIN, bitCoinStocks));
    }

    private void renewDomesticStocksCode() {
        log.info("국내주식 실시간 종목 코드 및 종목명을 갱신합니다.");
        List<StockInfoRequest> domesticStocks = stockApiCaller.getStockCodes(StockType.DOMESTIC_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.DOMESTIC_STOCK, domesticStocks));
    }

    private void renewOverseasStocksCode() {
        log.info("해외주식 실시간 종목 코드 및 종목명을 갱신합니다.");
        List<StockInfoRequest> overSeasStocks = stockApiCaller.getStockCodes(StockType.OVERSEAS_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.OVERSEAS_STOCK, overSeasStocks));
    }

}
