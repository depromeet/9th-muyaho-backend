package com.depromeet.muyaho.service.memberstock.dto.request;

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

    public static UpdateMemberStockRequest testInstance(Long memberStockId, double purchasePrice, double quantity) {
        return new UpdateMemberStockRequest(memberStockId, purchasePrice, quantity);
    }

}
