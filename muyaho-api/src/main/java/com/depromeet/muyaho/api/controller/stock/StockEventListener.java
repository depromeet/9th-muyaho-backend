package com.depromeet.muyaho.api.controller.stock;

import com.depromeet.muyaho.api.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.domain.service.stock.StockRenewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockEventListener {

    private final StockRenewService stockRenewService;

    @EventListener
    public void renewStockInfo(RequestedRenewEvent event) {
        stockRenewService.renewStock(event.getType(), event.getStockInfos());
    }

}
