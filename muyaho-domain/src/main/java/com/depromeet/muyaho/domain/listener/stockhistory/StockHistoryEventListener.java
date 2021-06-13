package com.depromeet.muyaho.domain.listener.stockhistory;

import com.depromeet.muyaho.domain.event.memberstock.MemberStockDeletedEvent;
import com.depromeet.muyaho.domain.service.stockhistory.StockHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StockHistoryEventListener {

    private final StockHistoryService stockHistoryService;

    @EventListener
    public void deleteMemberStockHistory(MemberStockDeletedEvent event) {
        stockHistoryService.deleteMemberStockHistory(event.getMemberStockId(), event.getMemberId());
    }

}
