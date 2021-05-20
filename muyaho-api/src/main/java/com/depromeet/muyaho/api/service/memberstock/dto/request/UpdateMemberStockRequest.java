package com.depromeet.muyaho.api.service.memberstock.dto.request;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberStockRequest {

    @NotNull
    private Long memberStockId;

    @NotNull
    private double purchasePrice;

    @NotNull
    private double quantity;

    @NotNull
    private CurrencyType currencyType;

    public static UpdateMemberStockRequest testInstance(Long memberStockId, double purchasePrice, double quantity, CurrencyType currencyType) {
        return new UpdateMemberStockRequest(memberStockId, purchasePrice, quantity, currencyType);
    }

}
