package com.depromeet.muyaho.api.service.memberstock.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
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
