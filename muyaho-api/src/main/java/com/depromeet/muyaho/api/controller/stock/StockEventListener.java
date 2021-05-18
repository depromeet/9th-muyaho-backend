package com.depromeet.muyaho.api.controller.stock;

import com.depromeet.muyaho.api.event.stock.RequestedRenewEvent;
import com.depromeet.muyaho.api.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockEventListener {

    private final StockService stockService;

    @EventListener
    public void renewStockInfo(RequestedRenewEvent event) {
        stockService.renewStock(event.getType(), event.getStockInfos());
    }

}