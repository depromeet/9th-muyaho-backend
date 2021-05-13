package com.depromeet.muyaho.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.utils.DecimalStringUtils;
import lombok.*;

@ToString
@Getter
public class MemberStockCurrentInfoResponse {

    private final Long memberStockId;
    private final StockInfoResponse stock;
    private final String purchasePrice;
    private final String quantity;
    private final String currentPrice;
    private final String currentTotalPrice;
    private final String purchaseTotalPrice;
    private final String earningRate;

    @Builder
    private MemberStockCurrentInfoResponse(Long memberStockId, StockInfoResponse stock, double quantity, double purchasePrice, double currentPrice) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchasePrice = DecimalStringUtils.covertToString(purchasePrice);
        this.quantity = DecimalStringUtils.covertToString(quantity);
        this.currentPrice = DecimalStringUtils.covertToString(currentPrice);
        this.currentTotalPrice = DecimalStringUtils.covertToString(quantity * currentPrice);
        this.purchaseTotalPrice = DecimalStringUtils.covertToString(quantity * purchasePrice);
        this.earningRate = calculateEarningRate(currentPrice, purchasePrice);
    }

    public static MemberStockCurrentInfoResponse of(MemberStock memberStock, double currentPrice) {
        return MemberStockCurrentInfoResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .quantity(memberStock.getQuantity())
            .purchasePrice(memberStock.getPurchasePrice())
            .currentPrice(currentPrice)
            .build();
    }

    private String calculateEarningRate(double currentPrice, double purchasePrice) {
        if (currentPrice >= purchasePrice) {
            return DecimalStringUtils.covertToString((currentPrice - purchasePrice) / purchasePrice * 100);
        }
        return DecimalStringUtils.covertToString((purchasePrice - currentPrice) / currentPrice * (-100));
    }

}
