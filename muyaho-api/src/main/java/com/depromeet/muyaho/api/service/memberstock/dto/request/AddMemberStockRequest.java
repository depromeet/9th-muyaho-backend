package com.depromeet.muyaho.api.service.memberstock.dto.request;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMemberStockRequest {

    @NotNull
    private Long stockId;

    @NotNull
    private double purchasePrice;

    @NotNull
    private double quantity;

    @NotNull
    private CurrencyType currencyType;

    public MemberStock toEntity(Long memberId, Stock stock) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, currencyType);
    }

    public static AddMemberStockRequest testInstance(Long stockId, double purchasePrice, double quantity, CurrencyType currencyType) {
        return new AddMemberStockRequest(stockId, purchasePrice, quantity, currencyType);
    }

}
