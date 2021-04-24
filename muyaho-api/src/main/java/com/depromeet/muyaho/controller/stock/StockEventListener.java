package com.depromeet.muyaho.controller.stock;

import com.depromeet.muyaho.event.stock.CameRenewRequestEvent;
import com.depromeet.muyaho.service.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockEventListener {

    private final StockService stockService;

    @EventListener
    public void renewStockInfo(CameRenewRequestEvent event) {
        stockService.renewStock(event.getType(), event.getStockInfos());
    }

}
