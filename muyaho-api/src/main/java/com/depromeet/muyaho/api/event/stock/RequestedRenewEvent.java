package com.depromeet.muyaho.api.event.stock;

import com.depromeet.muyaho.api.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
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
