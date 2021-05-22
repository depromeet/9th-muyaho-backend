package com.depromeet.muyaho.api.service.memberstock.dto.response;

import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCurrentResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockPurchaseResponse;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OverSeaCalculateResponse {

    private final Long memberStockId;
    private final StockInfoResponse stock;

    private final StockPurchaseResponse purchase;
    private final StockCurrentResponse currentInDollar;
    private final StockCurrentResponse currentInWon;

    private final CurrencyType currencyType;
    private final String quantity;
    private final String earningRate;

    @Builder
    public OverSeaCalculateResponse(Long memberStockId, StockInfoResponse stock, StockPurchaseResponse purchase,
                                    StockCurrentResponse currentInDollar, StockCurrentResponse currentInWon,
                                    CurrencyType currencyType, String quantity, String earningRate) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.purchase = purchase;
        this.currentInDollar = currentInDollar;
        this.currentInWon = currentInWon;
        this.currencyType = currencyType;
        this.quantity = quantity;
        this.earningRate = earningRate;
    }

    public static OverSeaCalculateResponse of(StockCalculateResponse stockCalculateResponse, BigDecimal currentPriceInWon) {
        return OverSeaCalculateResponse.builder()
            .memberStockId(stockCalculateResponse.getMemberStockId())
            .stock(stockCalculateResponse.getStock())
            .purchase(stockCalculateResponse.getPurchase())
            .currentInDollar(stockCalculateResponse.getCurrent())
            .currentInWon(StockCurrentResponse.of(currentPriceInWon, new BigDecimal(stockCalculateResponse.getQuantity())))
            .currencyType(stockCalculateResponse.getCurrencyType())
            .quantity(stockCalculateResponse.getQuantity())
            .earningRate(stockCalculateResponse.getEarningRate())
            .build();
    }

    public BigDecimal getPurchaseAmountPriceInWon() {
        return new BigDecimal(purchase.getAmountInWon());
    }

    public BigDecimal getCurrentAmountPriceInWon() {
        return new BigDecimal(currentInWon.getAmount());
    }

}
