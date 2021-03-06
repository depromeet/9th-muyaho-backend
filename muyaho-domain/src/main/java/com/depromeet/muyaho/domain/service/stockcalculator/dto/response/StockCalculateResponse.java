package com.depromeet.muyaho.domain.service.stockcalculator.dto.response;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;
import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCalculateResponse {

    private Long memberStockId;
    private StockInfoResponse stock;

    private StockPurchaseResponse purchase;
    private StockCurrentResponse current;

    private String quantity;
    private CurrencyType currencyType;
    private String profitOrLoseRate;

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

    public static StockCalculateResponse of(StockHistory history) {
        final MemberStock memberStock = history.getMemberStock();
        return StockCalculateResponse.builder()
            .memberStockId(memberStock.getId())
            .stock(StockInfoResponse.of(memberStock.getStock()))
            .purchase(StockPurchaseResponse.of(memberStock.getPurchaseUnitPrice(), memberStock.getQuantity(), memberStock.getPurchaseTotalPriceInWon()))
            .current(StockCurrentResponse.of(memberStock.getQuantity(), history.getCurrentPriceInWon(), history.getCurrentPriceInDollar()))
            .quantity(roundFloor(memberStock.getQuantity()))
            .currencyType(memberStock.getCurrencyType())
            .profitOrLoseRate(roundFloor(history.getProfitOrLoseRate()))
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
