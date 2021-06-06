package com.depromeet.muyaho.domain.service.memberstock.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberStockRequest {

    @NotNull(message = "{memberStock.id.notnull}")
    private Long memberStockId;

    @NotNull(message = "{stock.purchasePrice.notnull}")
    private BigDecimal purchasePrice;

    @NotNull(message = "{stock.quantity.notnull}")
    private BigDecimal quantity;

    private BigDecimal purchaseTotalPrice;

    public static UpdateMemberStockRequest testInstance(Long memberStockId, BigDecimal purchasePrice, BigDecimal quantity, BigDecimal totalPurchasePrice) {
        return new UpdateMemberStockRequest(memberStockId, purchasePrice, quantity, totalPurchasePrice);
    }

}
