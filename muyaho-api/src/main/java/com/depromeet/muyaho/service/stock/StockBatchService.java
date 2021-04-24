package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.event.stock.CameRenewRequestEvent;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StockBatchService {

    private final ApplicationEventPublisher eventPublisher;
    private final UpBitApiCaller upBitApiCaller;

    public void renewBitCoin() {
        List<StockInfoRequest> stockInfoList = upBitApiCaller.retrieveMarkets().stream()
            .map(market -> StockInfoRequest.of(market.getMarket(), market.getKoreanName()))
            .collect(Collectors.toList());
        eventPublisher.publishEvent(CameRenewRequestEvent.of(StockMarketType.BITCOIN, stockInfoList));
    }

}
