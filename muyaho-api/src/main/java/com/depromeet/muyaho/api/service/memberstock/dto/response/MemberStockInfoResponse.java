package com.depromeet.muyaho.api.service.memberstock.dto.response;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import lombok.*;

import java.math.BigDecimal;

import static com.depromeet.muyaho.common.utils.BigDecimalUtils.roundFloor;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberStockInfoResponse {

    private Long memberStockId;
    private StockInfoResponse stock;

    private CurrencyType currencyType;
    private String quantity;
    private String purchasePrice;
    private String purchaseAmount;
    private String purchaseAmountInWon;

    @Builder
    private MemberStockInfoResponse(Long memberStockId, StockInfoResponse stock, CurrencyType currencyType, BigDecimal purchasePrice, BigDecimal quantity, BigDecimal purchaseAmountInWon) {
        this.memberStockId = memberStockId;
        this.stock = stock;
        this.currencyType = currencyType;
        this.purchasePrice = roundFloor(purchasePrice);
        this.quantity = roundFloor(quantity);
        this.purchaseAmount = roundFloor(quantity.multiply(purchasePrice));
        this.purchaseAmountInWon = purchaseAmountInWon == null ? null : roundFloor(purchaseAmountInWon);
    }

    public static MemberStockInfoResponse of(MemberStock memberStock, Stock stock) {
        return new MemberStockInfoResponse(memberStock.getId(), StockInfoResponse.of(stock), memberStock.getCurrencyType(),
            memberStock.getPurchaseUnitPrice(), memberStock.getQuantity(), memberStock.getPurchaseTotalPriceInWon());
    }

}
