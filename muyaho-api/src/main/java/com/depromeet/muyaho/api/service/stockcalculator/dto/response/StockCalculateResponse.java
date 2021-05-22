package com.depromeet.muyaho.api.service.stockcalculator.dto.response;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.*;

@ToString
@Getter
public class StockCalculateResponse {

    private final Long memberStockId;

    private final StockInfoResponse stock;

    private final StockPurchaseResponse purchase;

    private final StockCurrentResponse current;

    private final CurrencyType currencyType;

    private final String quantity;

    private final String earningRate;

    @Builder
    private StockCalculateResponse(Long memberStockId, StockInfoResponse stock, CurrencyType currencyType,
                                   BigDecimal purchaseQuantity, BigDecimal purchaseUnitPrice, BigDecimal currentUnitPrice, BigDecimal purchaseAmountInWon) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.currencyType = currencyType;
        this.quantity = roundFloor(purchaseQuantity);
        this.purchase = StockPurchaseResponse.of(purchaseUnitPrice, purchaseQuantity, purchaseAmountInWon);
        this.current = StockCurrentResponse.of(currentUnitPrice, purchaseQuantity);
        this.earningRate = roundFloor(calculateDifferencePercent(currentUnitPrice, purchaseUnitPrice));
    }

    public static StockCalculateResponse of(MemberStock memberStock, BigDecimal currentPrice) {
        return StockCalculateResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .currencyType(memberStock.getCurrencyType())
            .purchaseQuantity(memberStock.getQuantity())
            .purchaseUnitPrice(memberStock.getPurchaseUnitPrice())
            .currentUnitPrice(currentPrice)
            .purchaseAmountInWon(memberStock.getPurchaseTotalPriceInWon())
            .build();
    }

    public static StockCalculateResponse testInstance(Long memberStockId, BigDecimal quantity, BigDecimal purchasePrice, BigDecimal currentPrice) {
        return StockCalculateResponse.builder()
            .memberStockId(memberStockId)
            .purchaseQuantity(quantity)
            .purchaseUnitPrice(purchasePrice)
            .currentUnitPrice(currentPrice)
            .build();
    }

    public BigDecimal takeCurrentUnitPrice() {
        return new BigDecimal(current.getUnitPrice());
    }

    public BigDecimal takePurchaseAmountPrice() {
        return new BigDecimal(purchase.getAmount());
    }

    public BigDecimal takeCurrentAmountPrice() {
        return new BigDecimal(current.getAmount());
    }

}
