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

    private final String quantity;
    private final CurrencyType currencyType;
    private final String profitOrLoseRate;

    @Builder
    public StockCalculateResponse(Long memberStockId, StockInfoResponse stock, StockPurchaseResponse purchase,
                                  StockCurrentResponse current, String quantity, CurrencyType currencyType, String profitOrLoseRate) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchase = purchase;
        this.current = current;
        this.quantity = quantity;
        this.currencyType = currencyType;
        this.profitOrLoseRate = profitOrLoseRate;
    }

    public static StockCalculateResponse of(MemberStock memberStock, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        return StockCalculateResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .purchase(StockPurchaseResponse.of(memberStock.getPurchaseUnitPrice(), memberStock.getQuantity(), memberStock.getPurchaseTotalPriceInWon()))
            .current(StockCurrentResponse.of(memberStock.getQuantity(), currentPriceInWon, currentPriceInDollar))
            .quantity(roundFloor(memberStock.getQuantity()))
            .currencyType(memberStock.getCurrencyType())
            .profitOrLoseRate(roundFloor(profitOrLoseRate))
            .build();
    }

    public BigDecimal takePurchaseAmountPrice() {
        return new BigDecimal(purchase.getAmount());
    }

    public BigDecimal takePurchaseAmountInWon() {
        return new BigDecimal(purchase.getAmountInWon());
    }

    public BigDecimal takeCurrentAmountPrice() {
        return new BigDecimal(current.getWon().getAmountPrice());
    }

}
