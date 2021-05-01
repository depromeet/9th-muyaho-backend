package com.depromeet.muyaho.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockInfoResponse {

    private final Long memberStockId;

    private final StockInfoResponse stock;

    private final double purchasePrice;

    private final double quantity;

    public static MemberStockInfoResponse of(MemberStock memberStock, Stock stock) {
        return new MemberStockInfoResponse(memberStock.getId(), StockInfoResponse.of(stock),
            memberStock.getPurchasePrice(), memberStock.getQuantity());
    }

}
