package com.depromeet.muyaho.api.service.memberstock.dto.response;

import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockPurchaseResponse;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MemberStockInfoResponse {

    private final Long memberStockId;

    private final StockInfoResponse stock;

    private final CurrencyType currencyType;

    private final StockPurchaseResponse purchase;

    @Builder
    private MemberStockInfoResponse(Long memberStockId, StockInfoResponse stock, CurrencyType currencyType, BigDecimal purchasePrice, BigDecimal quantity, BigDecimal totalPurchaseWon) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.currencyType = currencyType;
        this.purchase = StockPurchaseResponse.of(purchasePrice, quantity, totalPurchaseWon);
    }

    public static MemberStockInfoResponse of(MemberStock memberStock, Stock stock) {
        return new MemberStockInfoResponse(memberStock.getId(), StockInfoResponse.of(stock), memberStock.getCurrencyType(),
            memberStock.getPurchaseUnitPrice(), memberStock.getQuantity(), memberStock.getPurchaseTotalPriceInWon());
    }

}
