package com.depromeet.muyaho.api.service.memberstock.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberStockRequest {

    @NotNull
    private Long memberStockId;

    @NotNull
    private BigDecimal purchasePrice;

    @NotNull
    private BigDecimal quantity;

    private BigDecimal purchaseTotalPrice;

    public static UpdateMemberStockRequest testInstance(Long memberStockId, BigDecimal purchasePrice, BigDecimal quantity, BigDecimal totalPurchasePrice) {
        return new UpdateMemberStockRequest(memberStockId, purchasePrice, quantity, totalPurchasePrice);
    }

}
