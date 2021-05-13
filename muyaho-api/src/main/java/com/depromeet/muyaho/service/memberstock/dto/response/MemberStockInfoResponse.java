package com.depromeet.muyaho.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.utils.DecimalStringUtils;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberStockInfoResponse {

    private final Long memberStockId;

    private final StockInfoResponse stock;

    private final String purchasePrice;

    private final String quantity;

    @Builder
    private MemberStockInfoResponse(Long memberStockId, StockInfoResponse stock, double purchasePrice, double quantity) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchasePrice = DecimalStringUtils.covertToString(purchasePrice);
        this.quantity = DecimalStringUtils.covertToString(quantity);
    }

    public static MemberStockInfoResponse of(MemberStock memberStock, Stock stock) {
        return new MemberStockInfoResponse(memberStock.getId(), StockInfoResponse.of(stock),
            memberStock.getPurchasePrice(), memberStock.getQuantity());
    }

}
