package com.depromeet.muyaho.domain.event.stock;

import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestedRenewEvent {

    private final StockMarketType type;

    private final List<StockInfoRequest> stockInfos;

    public static RequestedRenewEvent of(StockMarketType type, List<StockInfoRequest> stockInfos) {
        return new RequestedRenewEvent(type, stockInfos);
    }

}
