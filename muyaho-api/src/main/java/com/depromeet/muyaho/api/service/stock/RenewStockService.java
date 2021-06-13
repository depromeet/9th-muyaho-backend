package com.depromeet.muyaho.api.service.stock;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.external.client.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.client.stock.StockApiCaller;
import com.depromeet.muyaho.external.client.stock.StockType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RenewStockService {

    private final ApplicationEventPublisher eventPublisher;
    private final UpBitApiCaller upBitApiCaller;
    private final StockApiCaller stockApiCaller;

    public void renewBitCoinStocksCode() {
        List<StockInfoRequest> bitCoinStocks = upBitApiCaller.fetchListedBitcoins().stream()
            .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.BITCOIN, bitCoinStocks));
        log.info("{}개의 비트코인 실시간 종목 코드 및 종목명을 갱신하였습니다.", bitCoinStocks.size());
    }

    public void renewDomesticStocksCode() {
        List<StockInfoRequest> domesticStocks = stockApiCaller.fetchListedStocksCodes(StockType.DOMESTIC_STOCK).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.DOMESTIC_STOCK, domesticStocks));
        log.info("{}개의 국내 주식 실시간 종목 코드 및 종목명을 갱신하였습니다.", domesticStocks.size());
    }

    public void renewOverseasStock() {
        eventPublisher.publishEvent(RequestedRenewEvent.of(StockMarketType.OVERSEAS_STOCK, fetchListedOverseasStock()));
        log.info("{}개의 해외주식 실시간 종목 코드 및 종목명을 갱신하였습니다.", fetchListedOverseasStock().size());
    }

    private List<StockInfoRequest> fetchListedOverseasStock() {
        // NASDAQ 증시
        List<StockInfoRequest> overSeasStocks = stockApiCaller.fetchListedStocksCodes(StockType.NASDAQ).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList());

        // NYSE 증시
        overSeasStocks.addAll(stockApiCaller.fetchListedStocksCodes(StockType.NYSE).stream()
            .map(market -> StockInfoRequest.of(market.getCode(), market.getName()))
            .collect(Collectors.toList()));

        return overSeasStocks.stream()
            .distinct()
            .collect(Collectors.toList());
    }

}
