package com.depromeet.muyaho.api.schedule;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.api.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.external.client.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.client.stock.StockApiCaller;
import com.depromeet.muyaho.external.client.stock.StockType;
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

    @Scheduled(cron = "0 0 8 * * *")
    public void renewBitCoinStocksCode() {
        List<StockInfoRequest> bitCoinStocks = upBitApiCaller.retrieveMarkets().stream()
            .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.BITCOIN, bitCoinStocks));
        log.info("{}개의 비트코인 실시간 종목 코드 및 종목명을 갱신하였습니다.. ", bitCoinStocks.size());
    }

    @Scheduled(cron = "1 0 8 * * *")
    public void renewDomesticStocksCode() {
        List<StockInfoRequest> domesticStocks = stockApiCaller.getStockCodes(StockType.DOMESTIC_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.DOMESTIC_STOCK, domesticStocks));
        log.info("{}개의 국내 주식 실시간 종목 코드 및 종목명을 갱신하였습니다.. ", domesticStocks.size());
    }

    @Scheduled(cron = "2 0 8 * * *")
    public void renewOverseasStocksCode() {
        List<StockInfoRequest> overSeasStocks = stockApiCaller.getStockCodes(StockType.OVERSEAS_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.OVERSEAS_STOCK, overSeasStocks));
        log.info("{}개의 해외 주식 실시간 종목 코드 및 종목명을 갱신하였습니다.. ", overSeasStocks.size());
    }

}
