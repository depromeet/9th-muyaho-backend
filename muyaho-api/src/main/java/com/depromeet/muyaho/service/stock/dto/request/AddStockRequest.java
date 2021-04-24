package com.depromeet.muyaho.service.stock.dto.request;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStockRequest {

    @NotBlank
    private String stockCode;

    @NotNull
    private StockType type;

    @Min(1)
    @NotNull
    private int purchasePrice;

    @NotNull
    @Min(1)
    private int quantity;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    public AddStockRequest(String stockCode, StockType type, int purchasePrice, int quantity) {
        this.stockCode = stockCode;
        this.type = type;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }

    public Stock toEntity(Long memberId) {
        return Stock.newInstance(memberId, stockCode, type, purchasePrice, quantity);
    }

}
