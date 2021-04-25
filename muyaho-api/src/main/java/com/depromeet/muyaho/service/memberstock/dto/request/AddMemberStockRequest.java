package com.depromeet.muyaho.service.memberstock.dto.request;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMemberStockRequest {

    @NotNull
    private Long stockId;

    @Min(1)
    @NotNull
    private int purchasePrice;

    @Min(1)
    @NotNull
    private int quantity;

    public MemberStock toEntity(Long memberId) {
        return MemberStock.of(memberId, stockId, purchasePrice, quantity);
    }

    public static AddMemberStockRequest testInstance(Long stockId, int purchasePrice, int quantity) {
        return new AddMemberStockRequest(stockId, purchasePrice, quantity);
    }

}
