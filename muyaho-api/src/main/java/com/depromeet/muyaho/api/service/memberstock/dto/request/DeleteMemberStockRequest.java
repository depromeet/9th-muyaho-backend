package com.depromeet.muyaho.api.service.memberstock.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
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
