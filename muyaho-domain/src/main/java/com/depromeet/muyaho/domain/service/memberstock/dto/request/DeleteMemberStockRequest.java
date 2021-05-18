package com.depromeet.muyaho.domain.service.memberstock.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteMemberStockRequest {

    @NotNull
    private Long memberStockId;

    public static DeleteMemberStockRequest testInstance(Long memberStockId) {
        return new DeleteMemberStockRequest(memberStockId);
    }

}
