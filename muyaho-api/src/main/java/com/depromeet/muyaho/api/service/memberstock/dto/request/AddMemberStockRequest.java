package com.depromeet.muyaho.api.service.memberstock.dto.request;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMemberStockRequest {

    @NotNull
    private Long stockId;

    @NotNull
    private BigDecimal purchasePrice;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private CurrencyType currencyType;

    private BigDecimal purchaseTotalPrice;

    public MemberStock toEntity(Long memberId, Stock stock) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, currencyType, purchaseTotalPrice);
    }

    public static AddMemberStockRequest testInstance(Long stockId, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal purchaseTotalPrice) {
        return new AddMemberStockRequest(stockId, purchasePrice, quantity, currencyType, purchaseTotalPrice);
    }

}
