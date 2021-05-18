package com.depromeet.muyaho.api.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.*;

@Getter
public class MemberStockInfoResponse {

    private final Long memberStockId;

    private final StockInfoResponse stock;

    private final String purchasePrice;

    private final String quantity;

    private final String purchaseAmount;

    @Builder
    private MemberStockInfoResponse(Long memberStockId, StockInfoResponse stock, BigDecimal purchasePrice, BigDecimal quantity) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchasePrice = roundFloor(purchasePrice);
        this.quantity = roundFloor(quantity);
        this.purchaseAmount = roundFloor(purchasePrice.multiply(quantity));
    }

    public static MemberStockInfoResponse of(MemberStock memberStock, Stock stock) {
        return new MemberStockInfoResponse(memberStock.getId(), StockInfoResponse.of(stock),
            memberStock.getPurchasePrice(), memberStock.getQuantity());
    }

}
