package com.depromeet.muyaho.service.stockcalculator.dto.response;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.utils.BigDecimalUtils.*;

@ToString
@Getter
public class StockCalculateResponse {

    private final Long memberStockId;
    private final StockInfoResponse stock;
    private final String quantity;
    private final String purchasePrice;
    private final String purchaseAmount;
    private final String currentPrice;
    private final String currentAmount;
    private final String earningRate;

    @Builder
    private StockCalculateResponse(Long memberStockId, StockInfoResponse stock, BigDecimal quantity, BigDecimal purchasePrice, BigDecimal currentPrice) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.quantity = roundFloor(quantity);
        this.purchasePrice = roundFloor(purchasePrice);
        this.purchaseAmount = roundFloor(quantity.multiply(purchasePrice));
        this.currentPrice = roundFloor(currentPrice);
        this.currentAmount = roundFloor(quantity.multiply(currentPrice));
        this.earningRate = roundFloor(calculateDifferencePercent(currentPrice, purchasePrice));
    }

    public static StockCalculateResponse of(MemberStock memberStock, BigDecimal currentPrice) {
        return StockCalculateResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .quantity(memberStock.getQuantity())
            .purchasePrice(memberStock.getPurchasePrice())
            .currentPrice(currentPrice)
            .build();
    }

    public static StockCalculateResponse testInstance(Long memberStockId, BigDecimal quantity, BigDecimal purchasePrice, BigDecimal currentPrice) {
        return StockCalculateResponse.builder()
            .memberStockId(memberStockId)
            .quantity(quantity)
            .purchasePrice(purchasePrice)
            .currentPrice(currentPrice)
            .build();
    }

}
