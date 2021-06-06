package com.depromeet.muyaho.domain.service.memberstock.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteMemberStockRequest {

    @NotNull(message = "{memberStock.id.notnull}")
    private Long memberStockId;

    public static DeleteMemberStockRequest testInstance(Long memberStockId) {
        return new DeleteMemberStockRequest(memberStockId);
    }

}
