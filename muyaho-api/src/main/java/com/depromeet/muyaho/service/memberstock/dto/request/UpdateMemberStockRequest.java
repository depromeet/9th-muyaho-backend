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
    private int purchasePrice;

    @NotNull
    private int quantity;

    public static UpdateMemberStockRequest testInstance(Long memberStockId, int purchasePrice, int quantity) {
        return new UpdateMemberStockRequest(memberStockId, purchasePrice, quantity);
    }

}
