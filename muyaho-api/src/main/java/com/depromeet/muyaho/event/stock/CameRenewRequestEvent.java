package com.depromeet.muyaho.event.stock;

import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CameRenewRequestEvent {

    private final StockMarketType type;

    private final List<StockInfoRequest> stockInfos;

    public static CameRenewRequestEvent of(StockMarketType type, List<StockInfoRequest> stockInfos) {
        return new CameRenewRequestEvent(type, stockInfos);
    }

}
