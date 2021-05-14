package com.depromeet.muyaho.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.utils.BigDecimalUtils.*;

@ToString
@Getter
public class MemberStockCurrentInfoResponse {

    private final Long memberStockId;
    private final StockInfoResponse stock;
    private final String quantity;
    private final String purchasePrice;
    private final String currentPrice;
    private final String currentTotalPrice;
    private final String purchaseTotalPrice;
    private final String earningRate;

    @Builder
    private MemberStockCurrentInfoResponse(Long memberStockId, StockInfoResponse stock, BigDecimal quantity, BigDecimal purchasePrice, BigDecimal currentPrice) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchasePrice = roundFloor(purchasePrice);
        this.quantity = roundFloor(quantity);
        this.currentPrice = roundFloor(currentPrice);
        this.currentTotalPrice = roundFloor(quantity.multiply(currentPrice));
        this.purchaseTotalPrice = roundFloor(quantity.multiply(purchasePrice));
        this.earningRate = roundFloor(calculateDifferencePercent(currentPrice, purchasePrice));
    }

    public static MemberStockCurrentInfoResponse of(MemberStock memberStock, BigDecimal currentPrice) {
        return MemberStockCurrentInfoResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .quantity(memberStock.getQuantity())
            .purchasePrice(memberStock.getPurchasePrice())
            .currentPrice(currentPrice)
            .build();
    }

}
